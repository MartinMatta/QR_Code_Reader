package com.example.qrcode

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = BlankFragment()
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
                    //text.text = "scan"
                    true
                }
                R.id.generate -> {
                    //text.text = "generate"
                    true
                }
                R.id.history -> {
                   // text.text = "history"
                    true
                }
                else -> false
            }
        }


        //scanQRCode()
        //startActivityForResult(Intent(this@MainActivity, QrCodeActivity::class.java),101)

    }

    private fun scanQRCode(){
        val integrator = IntentIntegrator(this).apply {
            captureActivity = CaptureActivity::class.java
            setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            setPrompt("")
        }
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            else Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}