package com.example.qrcode

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.qrcode.Fragments.GenerateFragment
import com.example.qrcode.Fragments.HistoryFragment
import com.example.qrcode.Fragments.ScanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        window.statusBarColor = Color.parseColor("#ffffff");

        if (savedInstanceState == null) {
            startFragment(ScanFragment())
        }

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.scan

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.generate -> {
                    startFragment(GenerateFragment())
                    true
                }
                R.id.scan -> {
                    startFragment(ScanFragment())
                    true
                }
                R.id.history -> {
                    startFragment(HistoryFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun startFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame, fragment)
                .commit()
    }

}