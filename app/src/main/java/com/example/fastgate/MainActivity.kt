package com.example.fastgate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        supportActionBar!!.hide()
    }


    fun signup(view:View){

        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }

        val email = txtEmail.text.toString()
        val pass= txtPass.text.toString()
        val cpass =  txtconfirm.text.toString()

        if( email !="" && pass !="" && cpass !="")
        {
            if(pass == cpass){

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
                            auth.signOut()



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("Signup", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            Log.i("Signup" , "NULL")
                        }
                    }
            }

            else{
                Toast.makeText(baseContext, "Password do not match /nType correctly",
                    Toast.LENGTH_SHORT).show()
            }
        }

        else{
            Toast.makeText(this,"All fields are necessary!!" , Toast.LENGTH_SHORT).show()
        }



    }


    fun backToLogin(view:View){

        val intent = Intent(this,Login::class.java)
        startActivity(intent)
        finish()

    }







}
