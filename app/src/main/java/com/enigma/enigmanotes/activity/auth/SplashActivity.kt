package com.enigma.enigmanotes.activity.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.enigma.enigmanotes.activity.add.AddActivity
import com.enigma.enigmanotes.activity.main.MainActivity
import com.enigma.enigmanotes.databinding.ActivitySplashBinding
import com.enigma.enigmanotes.preferences.AuthToken
import com.enigma.enigmanotes.preferences.dataStore
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler.postDelayed({

            lifecycleScope.launch {
                val token = getToken()
                if (token.isNotEmpty()) {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this@SplashActivity, LandingActivity::class.java)
                    startActivity(intent)
                }
                finish()
            }
        }, 3000)
    }

    private suspend fun getToken():String {
        val dataStore = AuthToken.getInstance(this.dataStore)
        return dataStore.getToken()
    }
}