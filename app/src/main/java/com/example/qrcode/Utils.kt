package com.example.qrcode

import android.content.Intent
import android.net.Uri
import android.util.Patterns
import androidx.core.content.ContextCompat.startActivity


class Utils {

    fun getQrCodeType(text: String):String {

        return when (true) {
            text.isValidEmail() -> "email"
            text.isValidPhone() -> "phone"
            text.isValidUrl() -> "url"
            text.isValidSMS() -> "sms"
            text.isValidWiFi() -> "wifi"
            else -> {
                "text"
            }
        }
    }

    private fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()

    private fun String.isValidSMS(): Boolean {
        if ("SMSTO:" in  this) {
            return true
        }
        return false
    }

    private fun String.isValidWiFi(): Boolean {
        if ("WIFI:" in  this) {
            return true
        }
        return false
    }

    private fun String.isValidEmail(): Boolean {
        if ("MATMSG:" in  this) { //mailto in this
            return true
        }
        return false
    }

    private fun String.isValidPhone(): Boolean {
        if ("tel:" in  this) {
            return true
        }
        return false
    }

    private fun String.isValidGeo(): Boolean {
        if ("Geo:" in  this) {
            return true
        }
        return false
    }



}