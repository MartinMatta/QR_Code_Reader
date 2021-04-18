package com.example.qrcode.Fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.Fragment
import com.example.qrcode.R
import com.google.zxing.WriterException


class addQRCodeFragment : Fragment() {

    private var bitmap: Bitmap? = null
    private var qrgEncoder: QRGEncoder? = null
    private var qrSpinner: Spinner? = null
    private var qrText: EditText? = null
    private var qrImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_add_qr_code, container, false)

        qrSpinner = view.findViewById(R.id.qrSpinner)
        qrText = view.findViewById(R.id.qrText)
        qrImage = view.findViewById(R.id.qrImage)

        //val saveButton: ImageButton = view.findViewById(R.id.buttonSave)
        //val shareButton: ImageButton = view.findViewById(R.id.buttonShare)


        //saveButton.setOnClickListener() {
        //}

        //shareButton.setOnClickListener() {
        //}

        val adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.birds,
                android.R.layout.simple_spinner_item
        )

        //adapter.setDropDownViewResource(R.layout.spinner_row)
        qrSpinner!!.adapter = adapter
        //qrSpinner.adapter = adapter

        return view
    }

    fun generateQRCode() {

        var type = qrSpinner?.selectedItem?.toString()

        when (type) {
            "text" -> type =  QRGContents.Type.TEXT
            "contact" -> type =  QRGContents.Type.CONTACT
            "sms" -> type =  QRGContents.Type.SMS
            "phone" -> type =  QRGContents.Type.PHONE
            "location" -> type =  QRGContents.Type.LOCATION
            "email" -> type =  QRGContents.Type.EMAIL
            else -> print("otherwise")
        }
        qrgEncoder = QRGEncoder(qrText?.text.toString(), null, type, 5)

        try {
            bitmap = qrgEncoder!!.encodeAsBitmap()
            qrImage?.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.e("Tag", e.toString())
        }
    }
}