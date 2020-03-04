package com.example.fastgate


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cardlayout.view.*


class VehicleAdapter(private val context: HomePage, private val vehicleList: ArrayList<HomePage.Incomming>) :
    RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardlayout, parent, false))
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vehicleNo.text = vehicleList.get(position).vehicle
        holder.date.text = vehicleList.get(position).date
        holder.time.text = vehicleList.get(position).time
        holder.type.text = vehicleList.get(position).type
        holder.itemView.setOnClickListener {
            Toast.makeText(context, vehicleList.get(position).vehicle, Toast.LENGTH_LONG).show()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vehicleNo = view.txtVno
        val date = view.txtdate
        val time = view.txtTime
        val type = view.txtType
    }
}
