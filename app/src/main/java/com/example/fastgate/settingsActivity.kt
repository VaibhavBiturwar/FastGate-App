package com.example.fastgate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.activity_settings.*

class settingsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    var uname = ""
    var mob = ""
    var flat = ""
    var block = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar!!.hide()
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        var bundel = intent.extras
        supportActionBar!!.hide()
        val uid = bundel!!.getString("uid")

        // Write a message to the database
        val path = "USER/" + uid + "/info"
        val myref = database.child(path)
        myref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                uname = p0.child("owner_name").value.toString()
                mob   = p0.child("mob").value.toString()
                block = p0.child("block").value.toString()
                flat  =  p0.child("flat_no").value.toString()
            } })





    }




        fun logout(view: View){
        auth.signOut()
        val intent = Intent(this,Login::class.java)
        startActivity(intent)
        finish()
    }





}
