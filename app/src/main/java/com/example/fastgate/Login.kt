package com.example.fastgate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        supportActionBar!!.hide()
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        if(currentUser != null){
            val intent = Intent(this,HomePage::class.java)
            intent.putExtra("email",currentUser!!.email)
            startActivity(intent)
            finish()
        }

    }

    fun signup(view: View){
        btnSignup.setTextColor(R.color.red)
        val intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
    }


    fun login(view:View){

        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }

        val email = txtEmail.text.toString()
        val password = txtPass.text.toString()

        if(email != "" &&  password != "" ){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.i("Login", "signInWithEmail:success")
                        val user = auth.currentUser
                        val intent = Intent(this,HomePage::class.java)
                        intent.putExtra("email",user!!.email)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else{
            Toast.makeText(this,"Please enter email and password" , Toast.LENGTH_SHORT).show()
        }


    }


}
