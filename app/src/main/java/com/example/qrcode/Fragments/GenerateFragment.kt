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
            //Toast.makeText(activity, "Clicked!", Toast.LENGTH_SHORT).show()
            addQRCodeDialog()
        }

        val database = QrCodeDatabase(requireContext(), "history")
        val data = database.readData()


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

    private fun addQRCodeDialog() {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.add_qrcode_dialog, null)
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)

        //val btnCancel = mDialogView.findViewById<Button>(R.id.btnCancel)
        //val btnOk = mDialogView.findViewById<Button>(R.id.btnOk)

        val  mAlertDialog = mBuilder.show()

        //btnCancel.setOnClickListener {
            //mAlertDialog.dismiss()
        //}

        //btnOk.setOnClickListener {
            //exitProcess(1)
        //}
    }

}

