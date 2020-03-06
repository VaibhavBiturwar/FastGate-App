package com.example.fastgate


import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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

        if( vehicleList.get(position).type == "Ola"){ holder.logo.setImageResource(R.drawable.olalogo) }
        else if( vehicleList.get(position).type == "Uber"){ holder.logo.setImageResource(R.drawable.uberlogo) }
        else if( vehicleList.get(position).type == "Rapido"){ holder.logo.setImageResource(R.drawable.rapidologo) }
        else if( vehicleList.get(position).type == "Amazon"){ holder.logo.setImageResource(R.drawable.amazonlogo) }
        else if( vehicleList.get(position).type == "Flipkart"){ holder.logo.setImageResource(R.drawable.flipkartlogo) }
        else if( vehicleList.get(position).type == "Swiggy"){ holder.logo.setImageResource(R.drawable.swiggylogo) }
        else if( vehicleList.get(position).type == "Zomato"){ holder.logo.setImageResource(R.drawable.zomatologo) }
        else if( vehicleList.get(position).type == "Snapdeal"){ holder.logo.setImageResource(R.drawable.snapdeallogo) }

        holder.itemView.setOnClickListener {

            val dialog = Dialog(context)
            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialog .setCancelable(false)
            dialog .setContentView(R.layout.popup)

            val license = dialog .findViewById(R.id.txtnum) as TextView
            license.text = vehicleList.get(position).vehicle
            val date = dialog .findViewById(R.id.txtDate) as TextView
            date.text = vehicleList.get(position).date
            val time = dialog .findViewById(R.id.txtTime) as TextView
            time.text = vehicleList.get(position).time
            val type = dialog .findViewById(R.id.txtType) as TextView
            type.text = vehicleList.get(position).type
            val logo = dialog.findViewById(R.id.imgLogo) as ImageView
            if( type.text == "Ola" ) logo.setImageResource(R.drawable.olalogo)
            else if( type.text == "Uber" ) logo.setImageResource(R.drawable.uberlogo)
            else if( type.text == "Rapido" ) logo.setImageResource(R.drawable.rapidologo)
            else if( type.text == "Amazon" ) logo.setImageResource(R.drawable.amazonlogo)
            else if( type.text == "Flipkart" ) logo.setImageResource(R.drawable.flipkartlogo)
            else if( type.text == "Snapdeal" ) logo.setImageResource(R.drawable.snapdeallogo)
            else if( type.text == "Swiggy" ) logo.setImageResource(R.drawable.swiggylogo)
            else if( type.text == "Zomato" ) logo.setImageResource(R.drawable.zomatologo)
            val yesBtn = dialog .findViewById(R.id.yesBtn) as Button
            val noBtn = dialog .findViewById(R.id.noBtn) as Button

            yesBtn.setOnClickListener { dialog .dismiss() }
            noBtn.setOnClickListener { dialog .dismiss() }
            dialog .show()







        }






    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vehicleNo = view.txtVno
        val date = view.txtdate
        val time = view.txtTime
        val type = view.txtType
        val logo = view.imglogo
    }
}
