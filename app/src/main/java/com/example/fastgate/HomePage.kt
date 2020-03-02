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
import java.nio.file.Files.exists
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlin.reflect.typeOf


class HomePage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()


        database = FirebaseDatabase.getInstance().reference

        var bundel = intent.extras
        txtEmail.setText(bundel!!.getString("email"))

        supportActionBar!!.hide()


        loaddata()

    }


    fun logout(view:View){
        auth.signOut()

        val intent = Intent(this,Login::class.java)
        startActivity(intent)
        finish()
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

        fun pr(){
            Log.i("INCOMMING" , date+"  "+time+"    "+type+"    "+vehicle)
        }
    }



    fun loaddata(){

        var values = ArrayList<Incomming>()

        val uid = auth.currentUser!!.uid
        Log.i("UID",uid)
        // Write a message to the database
        val path = "USER/" + uid + "/incomming"
        val myref = database.child(path)


        myref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
//
            }

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
                        values.add(Incomming(a,b,c,d))
                    }
                    for(x in values)
                    {
                        x.pr()
                    }
                }
                }


        })









    }







}