package com.example.dogapi.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dogapi.R
import com.example.dogapi.ui.fragments.DogFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, DogFragment())
                .commit()
        }
    }
}
