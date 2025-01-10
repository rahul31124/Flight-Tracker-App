package com.example.airvibes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.airvibes.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth


class SignupActivity : AppCompatActivity() {

    /*AIzaSyDV-D-U9FryFjpvtU4vdmcIYGi95hi8yfY*/
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}
