package com.example.airvibes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.content.Intent

import android.widget.Toast
import com.example.airvibes.databinding.ActivityLoginBinding

import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginbutton.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.signupbtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}
