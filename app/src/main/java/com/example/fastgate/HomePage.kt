package com.example.fastgate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home_page.*
import com.google.firebase.database.DatabaseError
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.collections.ArrayList



class HomePage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var database: DatabaseReference
    var lcounter = 0
    var rcounter = 0
    var x:AlertDialog? = null

    var username =""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)


        reqContainer.isVisible = false
        supportActionBar!!.hide()

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        var bundel = intent.extras


        if(iCheck())
        {
//        Loading Animation
            val builder =  AlertDialog.Builder(this)
            val inflator = this.layoutInflater
            builder.setView(inflator.inflate(R.layout.loading,null))
            builder.setCancelable(false)
            x = builder.create()
            x!!.show()
        }

//      USERNAME
        val uid = auth.currentUser!!.uid
        val path = "USER/" + uid + "/info/owner_name"
        val myref = database.child(path)
        myref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                username = p0.value.toString()
                txtEmail.setText(username)
            } })

//      Loading Recycler view
        loaddata()

//        Loading request Container
        loadReq()




//      Date & TIME
        val current = LocalDateTime.now()
        val dtstr = current.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
        val temp = dtstr.split(",")
        val dt = temp[1] +" ,"+ temp[2] +"  |  " + temp[0]
        txtCurrent.setText(dt)


    }


    fun setting(view:View){
        val intent = Intent(this,settingsActivity::class.java)
        intent.putExtra("uid",auth.currentUser!!.uid)
        intent.putExtra("username",username)
        startActivity(intent)
    }

    inner class Incomming(){
            var date: String = ""
            var time: String = ""
            var type: String = ""
            var vehicle: String = ""

        constructor(date:String,time:String,type:String,vehicle:String) : this() {
            this.date=date
            this.time=time
            this.type=type
            this.vehicle=vehicle
        }
    }

    fun loadReq(){

        val container = findViewById(R.id.reqContainer) as ConstraintLayout
        val uid = auth.currentUser!!.uid
        val path = "USER/" + uid + "/request"
        val myref = database.child(path)
        myref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            val Time = findViewById(R.id.txtTime) as TextView
            val Type = findViewById(R.id.txtType) as TextView
            val Number = findViewById(R.id.txtNumber) as TextView
            val Logo = findViewById(R.id.imageView3) as ImageView

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {

                    if( p0.child("permission").value.toString() == "None" ){

                        Log.i("PERMISSSION" , p0.child("time").value.toString())
                        Time.setText(p0.child("time").value.toString())

                        Log.i("PERMISSSION" , p0.child("type").value.toString())
                        val type = p0.child("type").value.toString()

                        Log.i("PERMISSSION" , p0.child("vehicle").value.toString())
                        Number.setText(p0.child("vehicle").value.toString())

                        Type.setText(type)
                        if( type == "Ola" ) Logo.setImageResource(R.drawable.olalogo)
                        else if( type == "Uber" ) Logo.setImageResource(R.drawable.uberlogo)
                        else if( type == "Rapido" ) Logo.setImageResource(R.drawable.rapidologo)
                        else if( type == "Amazon" ) Logo.setImageResource(R.drawable.amazonlogo)
                        else if( type == "Flipkart" ) Logo.setImageResource(R.drawable.flipkartlogo)
                        else if( type == "Snapdeal" ) Logo.setImageResource(R.drawable.snapdeallogo)
                        else if( type == "Swiggy" ) Logo.setImageResource(R.drawable.swiggylogo)
                        else if( type == "Zomato" ) Logo.setImageResource(R.drawable.zomatologo)
                        else Logo.setImageResource(R.drawable.visotorlogo1)

                        if( container.isVisible == false )
                            container.isVisible = true
                    }
                }
            }
        })
    }

    var values = ArrayList<Incomming>()
    fun loaddata(){
        val uid = auth.currentUser!!.uid
//        Log.i("UID",uid)
        // Write a message to the database
        val path = "USER/" + uid + "/incomming"
        val myref = database.child(path)

        myref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    values.clear()
                    val  child = p0.children
                    var z = ""
                    child.forEach{
                        z = it.key.toString()
                        val a = p0.child(z).child("date").value.toString()
                        val b = p0.child(z).child("time").value.toString()
                        val c = p0.child(z).child("type").value.toString()
                        val d = p0.child(z).child("vehicle").value.toString()
                        values.add(0,Incomming(a,b,c,d))

                    }
                    rcounter = values.size
                    if(lcounter != rcounter){
                        loadRec()
                        lcounter = rcounter
                    }
                }
                                                    } })
    }

    fun loadRec(){
        loaddata()
        layoutManager = LinearLayoutManager(this)
        rview.layoutManager = layoutManager
        rview.adapter = VehicleAdapter(this, values)
        x!!.dismiss()
    }

    fun iCheck():Boolean{

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if( isConnected == false )
        {
            val dialog = Dialog(this)
            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialog .setCancelable(false)
            dialog .setContentView(R.layout.netcheckpopup)
            dialog .show()
            return false
        }
        else
            return true
    }

    fun allowPermission(view:View){
        val uid = auth.currentUser!!.uid
        val path = "USER/" + uid + "/request/permission"
        val myref = database.child(path)
        myref.setValue("Yes")
        reqContainer.isVisible = false
    }

    fun denyPermission(view:View){
        val uid = auth.currentUser!!.uid
        val path = "USER/" + uid + "/request/permission"
        val myref = database.child(path)
        myref.setValue("No")
        reqContainer.isVisible = false
    }


}
