package com.example.qrcode.Fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.example.qrcode.QrCodeDatabase
import com.example.qrcode.R
import com.example.qrcode.Utils

class ScanCodeFragment : Fragment() {
    private lateinit var codeScanner: CodeScanner
    val utils = Utils()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                123)
        }

        return inflater.inflate(R.layout.fragment_scan_code, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val database = QrCodeDatabase(requireContext(), "history")

        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.TWO_DIMENSIONAL_FORMATS // all_format
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                //Toast.makeText(requireContext(), it.text, Toast.LENGTH_LONG).show()
                //var url = "martinmatta355@gmail.com"

                if (utils.getQrCodeType(it.text) == "url") {
                    database.insertHistory(it.text, "url")
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(it.text)
                    startActivity(intent)
                }

                //Toast.makeText(requireContext(), utils.getQrCodeType(it.text), Toast.LENGTH_LONG).show()
                ///if (!url.isValidUrl()) {
                    //Toast.makeText(requireContext(), "no url", Toast.LENGTH_LONG).show()
                //}else{
                    //Toast.makeText(requireContext(), "url", Toast.LENGTH_LONG).show()
                //}
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

}