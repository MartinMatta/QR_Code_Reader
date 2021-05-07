  package com.example.qrcode.Fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.qrcode.ListAdapter
import com.example.qrcode.Model
import com.example.qrcode.QrCodeDatabase
import com.example.qrcode.R
import kotlin.system.exitProcess

  class GenerateFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.theme?.applyStyle(R.style.Theme_Scan, false)
        //activity?.title = "Generate"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_generate, container, false)

        val addButton: ImageButton = view.findViewById(R.id.add_button)
        val listView: ListView = view.findViewById(R.id.listView)

        addButton.setOnClickListener() {
            val fragment = addQRCodeFragment()
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frame, fragment)
                    ?.commit();
        }

        val database = QrCodeDatabase(requireContext(), "myCode")

        val data = database.readMyCode(requireContext())

        if (data.isNotEmpty()) {
            listView.adapter = ListAdapter(
                    requireActivity(),
                    R.layout.row,
                    data
            )
        }

        listView.emptyView = view.findViewById(R.id.emptyElement);

        return view
    }
}

