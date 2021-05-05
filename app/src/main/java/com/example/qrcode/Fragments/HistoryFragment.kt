package com.example.qrcode.Fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.example.qrcode.ListAdapter
import com.example.qrcode.Model
import com.example.qrcode.QrCodeDatabase
import com.example.qrcode.R

class HistoryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //activity?.title = "History"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_history, container, false)
        val listView: ListView = view.findViewById(R.id.listView)

        val database = QrCodeDatabase(requireContext(), "history")

        val data = database.readHistory()

        if (data.isNotEmpty()) {
            listView.adapter = ListAdapter(
                requireActivity(),
                R.layout.row,
                data.asReversed()
            )
        }

        listView.emptyView = view.findViewById(R.id.emptyElement);
        
        return view
    }

}