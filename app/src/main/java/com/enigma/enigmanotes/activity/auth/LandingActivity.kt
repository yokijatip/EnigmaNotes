package com.enigma.enigmanotes.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enigma.enigmanotes.R
import com.enigma.enigmanotes.databinding.ActivityLandingBinding

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnLogin.setOnClickListener {
                startActivity(Intent(this@LandingActivity, LoginActivity::class.java))
                finish()
            }
            btnRegister.setOnClickListener {
                startActivity(Intent(this@LandingActivity, RegisterActivity::class.java))
                finish()
            }
        }

    }
}