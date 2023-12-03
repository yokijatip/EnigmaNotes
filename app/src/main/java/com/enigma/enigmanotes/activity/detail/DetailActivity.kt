package com.enigma.enigmanotes.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enigma.enigmanotes.R
import com.enigma.enigmanotes.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}