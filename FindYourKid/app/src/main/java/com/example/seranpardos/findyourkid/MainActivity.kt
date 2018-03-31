package com.example.seranpardos.findyourkid

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

/**
 * Data class that models the way school buses are represented in this application
 */

class ApiResponse(var school_buses: List<SchoolBus>)

class SchoolBus (
            val id: Int,
            val name: String,
            val description: String,
            val stops_url: String,
            val img_url: String
)


/**
 * MainActivity
 */
class MainActivity : AppCompatActivity() {

    //----------------------ONCREATE-------------------------
    /**
     * onCreate method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView_main.layoutManager = LinearLayoutManager(this)
       // recyclerView_main.adapter = MainAdapter()


        val url = "https://api.myjson.com/bins/10yg1t"
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
        var response = client.newCall(request).enqueue(object : Callback {
            //On failure throws a "Toast" message to user indicating the problem
            override fun onFailure(call: Call, e: IOException) {
                println("Failure fetching the data from the source. Send data to user")
            }
            //On success assigns the response in String to busList
            override fun onResponse(call: Call, response: Response){
                val response = response.body()?.string()

                /**
                 * To parse the Json received
                 */
                val gson = GsonBuilder().create()

                /**
                 * String where the retrieved JSON (the main one) will be saved
                 */
                val busList = gson.fromJson(response, ApiResponse::class.java)



                runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(busList)
                }
            }
        })


    }
}
