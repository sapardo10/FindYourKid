package com.example.seranpardos.findyourkid

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.school_bus.view.*

/**
 * Class that models the recyclerView to show the list of buses.
 * @param apiResponse ApiResponse object that containst the JSON to model each bus into the list
 */

class MainAdapter(val apiResponse: ApiResponse): RecyclerView.Adapter<CustomViewHolder>() {

    //number of items to display
    override fun getItemCount(): Int {
        return this.apiResponse.school_buses.count()
    }

    //To do when a viewHolder is created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        //it creates a new view to insert
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.school_bus, parent, false)
        return CustomViewHolder(cellForRow)
    }

    //It changes the values of the View
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //Values of the holder are filled with the current bus
        val bus = this.apiResponse.school_buses.get(position)
        holder.view.textView_name?.text = bus.name
        holder.view.textView_description?.text = bus.description
        Picasso.get().load(bus.img_url).into(holder.view.imageView_bus)
        holder.bus = bus
    }
}

/**
 * Class that models the school bus on that view. It also redirects to the details of each bus
 * passing the necessary data to do so
 * @param view View current view
 * @bus SchoolBus object that contains the information about the current bus
 */
class CustomViewHolder(val view: View, var bus: SchoolBus? =null): RecyclerView.ViewHolder(view) {

    /**
     * Object that search to maintain formality when passing to DetailsActivity
     */
    companion object {
        //Bus name
        val BUS_NAME_KEY =  "BUS_NAME"
        //Bus description
        val BUS_DESCRIPTION_KEY = "BUS_DESCRIPTION"
        //Url of the icon of the bus
        val BUS_IMAGE_KEY = "BUS_IMAGE"
        //Url of the stops of the bus
        val BUS_STOPS_URL = "BUS_STOPS"
    }

    /**
     * It enables the clickListener, so that when a bus is pressed it goes to the details
     */
    init {
        view.setOnClickListener {
            //Creating intent to go to DetailsActivity
            val intent = Intent(view.context, DetailsActivity::class.java)

            //Adding information of the bus
            intent.putExtra(BUS_NAME_KEY, bus?.name)
            intent.putExtra(BUS_DESCRIPTION_KEY, bus?.description)
            intent.putExtra(BUS_IMAGE_KEY, bus?.img_url)
            intent.putExtra(BUS_STOPS_URL, bus?.stops_url)

            //Starts the other activity
            view.context.startActivity(intent)

        }
    }
}