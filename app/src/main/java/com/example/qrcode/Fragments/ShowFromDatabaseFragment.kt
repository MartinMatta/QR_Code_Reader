package com.example.qrcode.Fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.qrcode.R


class ShowFromDatabaseFragment : Fragment() {

    private var qrType: String = ""
    private var qrData: String = ""
    private var qrDataType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.fragment_show_from_database, container, false)

        activity?.window?.statusBarColor =  Color.parseColor("#ffffff");

        val bundle = this.arguments

        if (bundle != null) {
            qrType = arguments!!.getString("QrType").toString()
            qrData = arguments!!.getString("QrData").toString()
        }

        val textQrType: TextView = view.findViewById(R.id.qr_type)
        val textQrData: TextView = view.findViewById(R.id.qr_data)

        val openButton: Button = view.findViewById(R.id.buttonOpen)
        val shareButton: Button = view.findViewById(R.id.buttonShare)


        return view
    }
}