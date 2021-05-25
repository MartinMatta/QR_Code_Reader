package com.example.qrcode.Fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.*
import com.example.qrcode.QrCodeDatabase
import com.example.qrcode.R
import com.example.qrcode.Utils

class ScanCodeFragment : Fragment() {
    private lateinit var codeScanner: CodeScanner
    val utils = Utils()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        activity?.window?.statusBarColor =  Color.parseColor("#0e5e9b");

        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                123)
        }

        return inflater.inflate(R.layout.fragment_scan_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val database = QrCodeDatabase(requireContext(), "history")

        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS // all_format
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {

                when (true) {
                    utils.getQrCodeType(it.text) == "url" -> startFragment("URL", it.text)
                    utils.getQrCodeType(it.text) == "phone" -> startFragment("Tel", it.text)
                    utils.getQrCodeType(it.text) == "sms" -> startFragment("SMS", it.text)
                    utils.getQrCodeType(it.text) == "email" -> startFragment("Email", it.text)
                    utils.getQrCodeType(it.text) == "text" -> startFragment("Text", it.text)
                    utils.getQrCodeType(it.text) == "wifi" -> startFragment("WiFi", it.text)
                    else -> {
                        "text"
                    }
                }
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            activity.runOnUiThread {
                Toast.makeText(requireContext(), "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        if(::codeScanner.isInitialized) {
            codeScanner?.startPreview()
        }
    }

    override fun onPause() {
        if(::codeScanner.isInitialized) {
            codeScanner?.releaseResources()
        }
        super.onPause()
    }

    fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()

    fun startFragment(type: String, data: String) { //add code type (barcode, qrcode)
        val bundle = Bundle()
        bundle.putString("QrType", type)
        bundle.putString("QrData", data)

        val fragment = Show_QR_Fragment()
        fragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame, fragment)
                ?.commit();
    }

}