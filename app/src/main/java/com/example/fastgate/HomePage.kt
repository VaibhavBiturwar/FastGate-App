package com.example.fastgate

import android.R.attr.data
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home_page.*
import com.google.firebase.database.DatabaseError
import androidx.annotation.NonNull
import android.R.attr.keySet
import android.app.Dialog
import android.content.Context
import java.nio.file.Files.exists
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.Window
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.netcheckpopup.*
import kotlinx.android.synthetic.main.popup.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.typeOf


class HomePage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var database: DatabaseReference
    var loadup = true
    var lcounter = 0
    var rcounter = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        var bundel = intent.extras
        supportActionBar!!.hide()

//        Internet Check
        iCheck()

        val uid = auth.currentUser!!.uid
        // Write a message to the database
        val path = "USER/" + uid + "/info/owner_name"
        val myref = database.child(path)
//      USERNAME
        myref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                txtEmail.setText( p0.value.toString())

            }
        })


        loaddata()

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
        val uid = auth.currentUser!!.uid
//        Log.i("UID",uid)
        // Write a message to the database
        val path = "USER/" + uid + "/request"
        val myref = database.child(path)
        myref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    Log.i("PERMISSSION" , p0.child("date").value.toString())
                    Log.i("PERMISSSION" , p0.child("time").value.toString())
                    Log.i("PERMISSSION" , p0.child("type").value.toString())
                    Log.i("PERMISSSION" , p0.child("vehicle").value.toString())
                    Log.i("PERMISSSION" , p0.child("permission").value.toString())

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
    }


    fun iCheck(){

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

        }
    }


    fun checkInternet():Boolean{
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }


}
