package com.example.seranpardos.findyourkid

import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.school_bus.view.*

class MainAdapter(val apiResponse: ApiResponse): RecyclerView.Adapter<CustomViewHolder>() {

    //number of items
    override fun getItemCount(): Int {
        return apiResponse.school_buses.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.school_bus, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //val busName = busesNames.get(position)
        val bus = apiResponse.school_buses.get(position)
        holder?.view?.textView_name?.text = bus.name
        holder?.view?.textView_description?.text = bus.description
        Picasso.get().load(bus.img_url).into(holder?.view?.imageView_bus)

        holder?.bus = bus
    }
}

class CustomViewHolder(val view: View, var bus: SchoolBus? =null): RecyclerView.ViewHolder(view) {

    companion object {
        val BUS_NAME_KEY =  "BUS_NAME"
        val BUS_DESCRIPTION_KEY = "BUS_DESCRIPTION"
        val BUS_IMAGE_KEY = "BUS_IMAGE"
        val BUS_STOPS_URL = "BUS_STOPS"
    }

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, Details_Activity::class.java)

            intent.putExtra(BUS_NAME_KEY, bus?.name)
            intent.putExtra(BUS_DESCRIPTION_KEY, bus?.description)
            intent.putExtra(BUS_IMAGE_KEY, bus?.img_url)
            intent.putExtra(BUS_STOPS_URL, bus?.stops_url)

            view.context.startActivity(intent)

        }
    }
}