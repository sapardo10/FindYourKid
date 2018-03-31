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

    val busesNames = listOf<String>("First Bus", "Second Bus", "Third Bus","Fourth Bus", "a", "b ")

    //number of items
    override fun getItemCount(): Int {
        return apiResponse.school_buses.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.school_bus, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //val busName = busesNames.get(position)
        val bus = apiResponse.school_buses.get(position)
        holder?.view?.textView_name?.text = bus.name
        holder?.view?.textView_description?.text = bus.description
        Picasso.get().load(bus.img_url).into(holder?.view?.imageView_bus)
        holder?.view?.constraint.setBackgroundColor(Color.DKGRAY)

    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
            val intent = Intent(view.context, Details_Activity::class.java)

            view.context.startActivity(intent)

        }
    }
}