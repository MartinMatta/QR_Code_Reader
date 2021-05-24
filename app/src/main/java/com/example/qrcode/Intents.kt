package com.example.qrcode

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity

class Intents {

    private fun editEmail(data: String): String {
        val message = data.split(":")
        var email = message[2].split(";")[0]
        var subject = message[3].split(";")[0]
        var msg = message[4].split(";")[0]
        return "$email\n$subject\n$msg"
    }

    private fun editSMS(data: String): String {
        val message = data.split(":")
        var number = message[1].split(";")[0]
        var msg = message[2].split(";")[0]
        return  "$number\n$msg"
    }

    fun getWifiInfo(): MutableMap<String?, String?> {
        var dataBlock: List<String>
        val wifiInfo: MutableMap<String?, String?> = HashMap()

        var dataset: String = "WIFI:T:WPA;S:janca;P:2gtd58cn1;;".replace("WIFI:", "")
        // return T:WPA;S:mynetwork;P:mypass;;
        dataBlock = dataset.split(";")

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
}