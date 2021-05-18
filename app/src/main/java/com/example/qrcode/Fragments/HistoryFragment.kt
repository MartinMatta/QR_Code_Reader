package com.example.qrcode.Fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
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

        activity?.window?.statusBarColor =  Color.parseColor("#ffffff");

        val view: View = inflater.inflate(R.layout.fragment_history, container, false)
        val listView: ListView = view.findViewById(R.id.listView)

        val database = QrCodeDatabase(requireContext(), "history")
        val data = database.readHistory()

        val adapter: ListAdapter = ListAdapter(
                requireActivity(),
                R.layout.row,
                data
        )

        if (data.isNotEmpty()) {
            listView.adapter = adapter
        }

        listView.setOnItemClickListener {parent, view, position, id ->
            var id = data[position]
            //Toast.makeText(requireContext(), id.id.toString(),Toast.LENGTH_SHORT).show()
            database.delete(id.id, database.TABLE_HISTORY)
            adapter.remove(data[position])
            adapter.notifyDataSetChanged()
        }

        listView.emptyView = view.findViewById(R.id.emptyElement);
        
        return view
    }

}