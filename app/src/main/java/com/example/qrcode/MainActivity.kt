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

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //theme.applyStyle(R.style.Theme_Scan, true)

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

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            return
        } else {
            exitDialog()
        }
        backPressedTime = System.currentTimeMillis()
    }

    private fun exitDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.exit_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

        val btnCancel = mDialogView.findViewById<Button>(R.id.btnCancel)
        val btnOk = mDialogView.findViewById<Button>(R.id.btnOk)

        val  mAlertDialog = mBuilder.show()

        btnCancel.setOnClickListener {
            mAlertDialog.dismiss()
        }

        btnOk.setOnClickListener {
            exitProcess(1)
        }
    }

    private fun startFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame, fragment)
                .commit()
    }

}