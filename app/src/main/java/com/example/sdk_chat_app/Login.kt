package com.example.sdk_chat_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var loginbtn: Button
    private lateinit var btnsignup: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edit_email)
        edtPass = findViewById(R.id.edit_pass)
        loginbtn = findViewById(R.id.login_btn)
        btnsignup = findViewById(R.id.signup_btn)

        btnsignup.setOnClickListener {
            val intent = Intent(this@Login,Signup::class.java)
            startActivity(intent)
        }

        loginbtn.setOnClickListener{
            val email = edtEmail.text.toString()
            val pass = edtPass.text.toString()
            login(email,pass)
        }

    }

    private fun login(email: String, password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@Login,"User does not exists!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}