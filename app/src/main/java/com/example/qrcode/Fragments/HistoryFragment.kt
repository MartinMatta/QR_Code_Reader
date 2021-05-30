package com.example.qrcode.Fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.qrcode.*

class HistoryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        activity?.window?.statusBarColor =  Color.parseColor("#ffffff");

        val view: View = inflater.inflate(R.layout.fragment_history, container, false)
        val listView: ListView = view.findViewById(R.id.listView)

        val database = QrCodeDatabase(requireContext(), "history")
        val data = database.readHistory()

        var adapter: ListAdapter = ListAdapter(
                requireActivity(),
                R.layout.row,
                data
        )

        if (data.isNotEmpty()) {
            listView.adapter = adapter
        }

        listView.setOnItemClickListener {parent, view, position, id ->
            //var id = data[position]
            //val deleteDialog = DeleteDialog(requireContext(), adapter, database.TABLE_HISTORY)
            //adapter = deleteDialog.show(data[position], database, id.id)
            //adapter.notifyDataSetChanged()
            startFragment("url", "https", "barcode")
        }

        listView.emptyView = view.findViewById(R.id.emptyElement);
        
        return view
    }

    fun startFragment(type: String, data: String, codeFormat: String) { //add code type (barcode, qrcode)
        val bundle = Bundle()
        bundle.putString("QrType", type)
        bundle.putString("QrData", data)
        bundle.putString("CodeType", codeFormat)

        val fragment = ShowFromDatabaseFragment()
        fragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame, fragment)
            ?.commit();
    }

}