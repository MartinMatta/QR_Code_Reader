package com.example.qrcode.Fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        val openButton: Button = view.findViewById(R.id.buttonOpen)
        val shareButton: Button = view.findViewById(R.id.buttonShare)

        textQrType.text = qrType

        when (true) {
            qrType == "URL" -> textQrData.text = qrData
            qrType == "SMS" -> textQrData.text = editSMS(qrData)
            qrType == "Tel" -> textQrData.text = qrData.split(":")[1]
            qrType == "Email" -> textQrData.text = editEmail(qrData)
            else -> {
                textQrData.text = qrData
            }
        }

        openButton.setOnClickListener {
            when (true) {
                qrType == "URL" -> openURL(qrData)
                qrType == "SMS" -> openSMS(qrData)
                qrType == "Tel" -> openPhone(qrData)
                qrType == "Email" -> openEmail(qrData)
                else -> {
                    textQrData.text = qrData
                }
            }
        }

        shareButton.setOnClickListener {
            when (true) {
                qrType == "URL" -> shareData(qrData)
                qrType == "SMS" -> shareSMS(qrData) // 00
                qrType == "Tel" -> sharePhone(qrData)
                qrType == "Email" -> shareEmail(qrData)
                else -> {
                    shareData(qrData) // 1
                }
            }
        }

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
        var number = message[1].split(";")[0]
        var msg = message[2].split(";")[0]
        return  "$number\n$msg"
    }

    private fun openURL(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun openPhone(number: String) {
        //val phone = number.replace("tel:", "")
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(number)
        startActivity(intent)
    }

    private fun openEmail(data: String) {
        val msgCode = data.split(":")
        val intent = Intent(Intent.ACTION_SEND)
        val addressees = arrayOf(msgCode[2].split(";")[0])
        intent.putExtra(Intent.EXTRA_EMAIL, addressees)
        intent.putExtra(Intent.EXTRA_SUBJECT, msgCode[3].split(";")[0])
        intent.putExtra(Intent.EXTRA_TEXT, msgCode[4].split(";")[0])
        intent.type = "message/rfc822"
        startActivity(Intent.createChooser(intent, "Send Email using:"));
    }

    private fun openSMS(data: String) {
        val smsCode = data.split(":")
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:" + smsCode[1])
        intent.putExtra("sms_body", smsCode[2]);
        startActivity(intent)
    }

    private fun shareData(url: String) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        //i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL")
        i.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(i, "Share URL"))
    }

    private fun sharePhone(data: String) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        val phone = data.replace("tel:", "")
        i.putExtra(Intent.EXTRA_TEXT, phone)
        startActivity(Intent.createChooser(i, "Share URL"))
    }

    private fun shareEmail(data: String) {
        val message = data.split(":")
        var email = message[2].split(";")[0]
        var subject = message[3].split(";")[0]
        var msg = message[4].split(";")[0]

        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        val text = "$email\n$subject\n$msg"
        i.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(i, "Share URL"))
    }

    private fun shareSMS(data: String) {
        val message = data.split(":")
        var number = message[1].split(";")[0]
        var msg = message[2].split(";")[0]

        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        val text = "$number\n$msg"
        i.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(i, "Share URL"))
    }

}

