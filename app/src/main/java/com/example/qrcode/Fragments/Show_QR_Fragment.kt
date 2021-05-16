package com.example.qrcode.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.qrcode.R

class Show_QR_Fragment : Fragment() {

    var qrType: String = ""
    var qrData: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        activity?.window?.statusBarColor =  Color.parseColor("#ffffff");

        val bundle = this.arguments

        if (bundle != null) {
            qrType = arguments!!.getString("QrType").toString()
            qrData = arguments!!.getString("QrData").toString()
        }

        var view = inflater.inflate(R.layout.fragment_show__qr, container, false)

        val textQrType: TextView = view.findViewById(R.id.qr_type)
        val textQrData: TextView = view.findViewById(R.id.qr_data)

        textQrType.text = qrType

        when (true) {
            qrType == "URL" -> textQrData.text = qrData
            qrType == "SMS" -> textQrData.text = "sms sprava"
            qrType == "Tel" -> textQrData.text = qrData.split(":")[1]
            qrType == "Email" -> textQrData.text = editEmail(qrData)
            else -> {
                textQrData.text = qrData
            }
        }
        //textQrData.text = qrData

        val backButton: ImageButton = view.findViewById(R.id.back_button)

        backButton.setOnClickListener() {
            val fragment = ScanCodeFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame, fragment)
                ?.commit();
        }
        return view
    }

    private fun editEmail(data: String): String {
        val message = data.split(":")
        var email = message[2].split(";")[0]
        var subject = message[3].split(";")[0]
        var msg = message[4].split(";")[0]
        return "$email\n$subject\n$msg"
    }

    private fun editSMS(data: String): String {
        val message = data.split(":")
        var email = message[2].split(";")[0]
        var subject = message[3].split(";")[0]
        var msg = message[4].split(";")[0]
        return "$email\n$subject\n$msg"
    }

}