package com.example.babbel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.babbel.databinding.ActivityMainBinding
import com.example.game.GameFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.container, GameFragment.newInstance()).commit()
    }
}
