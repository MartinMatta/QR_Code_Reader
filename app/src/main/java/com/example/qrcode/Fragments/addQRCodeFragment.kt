package com.example.qrcode.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Spinner
import com.example.qrcode.R

class addQRCodeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_add_qr_code, container, false)

        val spinner: Spinner = view.findViewById(R.id.spinner)

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
        spinner.adapter = adapter

        return view
    }
}