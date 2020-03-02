package com.example.fastgate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }


    fun signup(view:View){

        var email = txtEmail.text.toString()
        var pass= txtPass.text.toString()
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i("Signup", "createUserWithEmail:success")
                    val user = auth.currentUser
                    Log.i("Signup" , user.toString())



                    txtMessage.setText("Signup Successful \nYour Id is")
                    txtUid.isVisible = true
                    txtUid.setText(user!!.uid.toString())

                } else {
                    // If sign in fails, display a message to the user.
                    Log.i("Signup", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    Log.i("Signup" , "NULL")
                }

            }

    }








}
