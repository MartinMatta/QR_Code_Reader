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
            else -> {
                "text"
            }
        }
    }

    //fun openUrl(url: String) {
        //val uri: Uri = Uri.parse(url)
        //startActivity(Intent(Intent.ACTION_VIEW, uri))
        //startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    //}


    private fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()
    private fun String.isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()
    private fun String.isValidPhone(): Boolean = Patterns.PHONE.matcher(this).matches()

}