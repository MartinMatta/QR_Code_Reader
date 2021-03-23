package com.example.qrcode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = ScanFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame, fragment)
                .commit()
        }

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        //val text: TextView = findViewById(R.id.text)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.scan -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame, ScanFragment())
                        .commit()
                    true
                }
                R.id.generate -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame, GenerateFragment())
                        .commit()
                    true
                }
                R.id.history -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame, HistoryFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

}