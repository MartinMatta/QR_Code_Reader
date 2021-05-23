package com.example.qrcode.Fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
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
import androidmads.library.qrgenearator.QRGSaver
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.qrcode.MainActivity
import com.example.qrcode.QrCodeDatabase
import com.example.qrcode.R
import com.google.zxing.WriterException


class addQRCodeFragment : Fragment() {

    private var qrgEncoder: QRGEncoder? = null
    private var qrSpinner: Spinner? = null
    private var qrText: EditText? = null
    private var qrImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        activity?.window?.statusBarColor =  Color.parseColor("#ffffff");

        val view =  inflater.inflate(R.layout.fragment_add_qr_code, container, false)

        qrSpinner = view.findViewById(R.id.qrSpinner)
        qrText = view.findViewById(R.id.qrText)
        qrImage = view.findViewById(R.id.qrImage)

        val saveButton: Button = view.findViewById(R.id.buttonSave)
        val shareButton: Button = view.findViewById(R.id.buttonShare)

        val database = QrCodeDatabase(requireContext(), "history")

        qrText?.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    view.hideKeyboard()

                    var type = qrSpinner!!.selectedItem.toString()

                    val bitmap = database.generateQRCode(
                        requireContext(),
                        qrText!!.text.toString(),
                        type
                    )
                    qrImage?.setImageBitmap(bitmap)

                    return true
                }
                return false
            }
        })

        saveButton.setOnClickListener() {
            database.insertMyCode(qrText!!.text.toString(), qrSpinner!!.selectedItem.toString())
            //QRGSaver.save(savePath, edtValue.getText().toString().trim(), bitmap, QRGContents.ImageType.IMAGE_JPEG);
        }

        shareButton.setOnClickListener() {
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


    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

