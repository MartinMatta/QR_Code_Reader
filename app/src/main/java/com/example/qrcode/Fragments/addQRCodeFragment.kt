package com.example.qrcode.Fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.qrcode.MainActivity
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

        val saveButton: Button = view.findViewById(R.id.buttonSave)
        val shareButton: Button = view.findViewById(R.id.buttonShare)

        qrText?.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    generateQRCode()
                    view.hideKeyboard()


                    return true
                }
                return false
            }
        })

        saveButton.setOnClickListener() {
            Toast.makeText(requireContext(),
                    "save",
                    Toast.LENGTH_SHORT).show();
        }

        shareButton.setOnClickListener() {
            Toast.makeText(requireContext(),
                    "share",
                    Toast.LENGTH_SHORT).show();
        }

        val adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.generate_menu,
                android.R.layout.simple_spinner_item
        )

        //adapter.setDropDownViewResource(R.layout.spinner_row)
        qrSpinner!!.adapter = adapter

        return view
    }

    fun generateQRCode() {

        var text: String = qrText?.text.toString()

        if (TextUtils.isEmpty(text)) {
            Toast.makeText(requireContext(),
                    "Enter some text to generate QR Code",
                    Toast.LENGTH_SHORT).show();
        } else {

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

            qrgEncoder = QRGEncoder(qrText?.text.toString(), null, type, 3)

            try {
                bitmap = qrgEncoder!!.encodeAsBitmap()
                qrImage?.setImageBitmap(bitmap)
            } catch (e: WriterException) {
                Log.e("Tag", e.toString())
            }

        }

    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

