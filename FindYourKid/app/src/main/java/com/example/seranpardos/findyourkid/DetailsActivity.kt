package com.example.seranpardos.findyourkid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import okhttp3.*
import java.io.IOException
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng


/**
 * Class that models the response of the JSON for the stops
 */
class StopsResponse(var stops: List<Stop>)

/**
 * Class that model one Stop of one specific bus
 */
class Stop (
        val lat: Double,
        val lng: Double
)

@Suppress("NAME_SHADOWING")
/**
 * It handles the activity where the user can check the stops of the selected bus
 */
class DetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    //mMap GoogleMap
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //map
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Values of bus
        val navBarTitle = intent.getStringExtra(CustomViewHolder.BUS_NAME_KEY)
        supportActionBar?.title = navBarTitle
        val busDescription = intent.getStringExtra(CustomViewHolder.BUS_DESCRIPTION_KEY)
        val busImage = intent.getStringExtra(CustomViewHolder.BUS_IMAGE_KEY)
        Picasso.get().load(busImage).into(imageView_bus)
        textView_description.text = busDescription
    }

    /**
     * This fun is loaded when the Map in the activity is ready to use
     * @param googleMap GoogleMap map ready to use
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //reads the Url for the stops
        val stops = intent.getStringExtra(CustomViewHolder.BUS_STOPS_URL)
        //class the read function
        read(stops)
    }

    /**
     * function that constructs the HTTP request to retrieve the data from the url
     * given
     * Code based on: http://square.github.io/okhttp/
     * @param url String that contains the url from where it will be retrieve a response
     */
    private fun read(url: String) {

        //Starts an HTTP client (val)
        val client = OkHttpClient()

        //It constructs the HTTP request
        val request = Request.Builder().url(url).build()

        //It sends the request to the server (API)
        client.newCall(request).enqueue(object : Callback {
            //On failure throws a "Toast" message to user indicating the problem
            override fun onFailure(call: Call, e: IOException) {
                Thread.setDefaultUncaughtExceptionHandler { _, e -> System.err.println(e.message) }
            }
            //On success assigns the response in String to busList
            override fun onResponse(call: Call, response: Response){
                val response = response.body()?.string()

                //To parse the Json received
                val gson = GsonBuilder().create()

                //String where the retrieved JSON  will be saved
                val stopsResponse = gson.fromJson(response, StopsResponse::class.java)

                runOnUiThread {
                    //It will iterate over the stops and add a marker to the map in each position
                    for ((index, stop) in stopsResponse!!.stops.withIndex()) {
                        val options = MarkerOptions()
                        val point = LatLng(stop.lat, stop.lng)
                        options.position(point)
                        options.title("Parada número: $index")
                        mMap!!.addMarker(options)
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
                    }
                }
            }
        })
    }
}
