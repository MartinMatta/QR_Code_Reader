package com.example.qrcode

import android.R.attr.key
import android.content.Context.WIFI_SERVICE
import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import androidx.core.content.ContextCompat.getSystemService


class Intents {

    fun editEmail(data: String): String {
        val message = data.split(":")
        var email = message[2].split(";")[0]
        var subject = message[3].split(";")[0]
        var msg = message[4].split(";")[0]
        return "$email\n$subject\n$msg"
    }

    fun editSMS(data: String): String {
        val message = data.split(":")
        var number = message[1].split(";")[0]
        var msg = message[2].split(";")[0]
        return  "$number\n$msg"
    }

    fun editWiFi(data: String): String {
        val message = getWifiInfo(data)
        val authType = message["auth_type"]
        val ssid = message["ssid"]
        val psk = message["psk"]
        return  "Network Name: $ssid\nPassword: $psk\n"
    }

    fun getWifiInfo(data: String): MutableMap<String?, String?> {
        val wifiInfo: MutableMap<String?, String?> = HashMap()
        var dataBlock: List<String> = data.split(";")

        for (i in 0..dataBlock.size/2) {
            when (true) {
                dataBlock[i][0] == 'T' -> wifiInfo["auth_type"] = dataBlock[i].split(":")[1]
                dataBlock[i][0] == 'S' -> wifiInfo["ssid"] = dataBlock[i].split(":")[1]
                dataBlock[i][0] == 'P' -> wifiInfo["psk"] = dataBlock[i].split(":")[1]
                dataBlock[i][0] == 'H' -> wifiInfo["is_hidden"] = dataBlock[i].split(":")[1]
                dataBlock[i][0] == 'E' -> wifiInfo["eap"] = dataBlock[i].split(":")[1]
                dataBlock[i][0] == 'A' -> wifiInfo["anon"] = dataBlock[i].split(":")[1]
                dataBlock[i][0] == 'I' -> wifiInfo["myidentity"] = dataBlock[i].split(":")[1]

            }

        }

        return wifiInfo
        //println(map["auth_type"])
        //println(map["ssid"])
        //println(map["psk"])
    }



    fun urlActivity(url: String): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        return intent
    }

    fun phoneActivity(number: String): Intent {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(number)
        return intent
    }

    fun emailActivity(data: String): Intent {
        val msgCode = data.split(":")
        val intent = Intent(Intent.ACTION_SEND)
        val addressees = arrayOf(msgCode[2].split(";")[0])
        intent.putExtra(Intent.EXTRA_EMAIL, addressees)
        intent.putExtra(Intent.EXTRA_SUBJECT, msgCode[3].split(";")[0])
        intent.putExtra(Intent.EXTRA_TEXT, msgCode[4].split(";")[0])
        intent.type = "message/rfc822"
        return Intent.createChooser(intent, "Send Email using:")

    }

    fun smsActivity(data: String): Intent {
        val smsCode = data.split(":")
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:" + smsCode[1])
        intent.putExtra("sms_body", smsCode[2]);
        return intent
    }

    fun geoActivity(data: String): Intent {
        val location = data.split(":")
        val mapIntent: Intent = Uri.parse(
            "geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California"
        ).let { location ->
            // Or map point based on latitude/longitude
            val location: Uri = Uri.parse(data)
            return Intent(Intent.ACTION_VIEW, location)
        }
    }

    fun shareData(url: String): Intent {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_TEXT, url)
        return Intent.createChooser(i, "Share text")
    }

    fun sharePhone(data: String): Intent {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        val phone = data.replace("tel:", "")
        i.putExtra(Intent.EXTRA_TEXT, phone)
        //startActivity(Intent.createChooser(i, "Share URL"))
        return Intent.createChooser(i, "Share Phone")
    }

    fun shareEmail(data: String): Intent {
        val message = data.split(":")
        var email = message[2].split(";")[0]
        var subject = message[3].split(";")[0]
        var msg = message[4].split(";")[0]

        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        val text = "$email\n$subject\n$msg"
        i.putExtra(Intent.EXTRA_TEXT, text)
        return Intent.createChooser(i, "Share URL")
    }

    fun shareSMS(data: String): Intent {
        val message = data.split(":")
        var number = message[1].split(";")[0]
        var msg = message[2].split(";")[0]

        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        val text = "$number\n$msg"
        i.putExtra(Intent.EXTRA_TEXT, text)
        return Intent.createChooser(i, "Share URL")
    }

    fun shareWifi(data: String): Intent {
        val message = getWifiInfo(data)
        val authType = message["auth_type"]
        val ssid = message["ssid"]
        val psk = message["psk"]
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_TEXT, "Network Name: $ssid\nPassword: $psk\n")
        return Intent.createChooser(i, "Share text")
    }

}