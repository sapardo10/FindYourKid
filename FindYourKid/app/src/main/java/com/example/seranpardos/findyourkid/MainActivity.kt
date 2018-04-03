package com.example.seranpardos.findyourkid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

/**
 * Class that models the way school buses are delivered on the JSON in this application
 */
class ApiResponse(var school_buses: List<SchoolBus>)

/**
 * Class that models the way the buses are represented in this application
 */
class SchoolBus (
            val id: Int,
            val name: String,
            val description: String,
            val stops_url: String,
            val img_url: String
)


@Suppress("NAME_SHADOWING")
/**
 * MainActivity. It shows a list of the school buses based on the JSON retrieved
 * Second Activity of the App
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Assigns the layout manager to the recyclerView
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        //Url from which the JSON is retrieved
        val url = "https://api.myjson.com/bins/10yg1t"
        //Call to read the data on the url
        read(url)

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
                val response: String? = response.body()?.string()

                /**
                 * To parse the Json received
                 */
                val gson = GsonBuilder().create()

                /**
                 * String where the retrieved JSON (the main one) will be saved
                 */
                val busList = gson.fromJson(
                        response,
                        ApiResponse::class.java
                )


                //Since this changes the UI, it has to go on main Thread
                runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(busList)
                }
            }
        })


    }
}
