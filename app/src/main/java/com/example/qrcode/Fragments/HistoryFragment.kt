package com.example.qrcode.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.qrcode.ListAdapter
import com.example.qrcode.Model
import com.example.qrcode.R

class HistoryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //activity?.title = "History"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_history, container, false)
        val listView: ListView = view.findViewById(R.id.listView)

        var list = mutableListOf<Model>()

        list.add(
            Model(
                "http://kristianvalčo.eu",
                "27/3/2021 pm 12:54",
                R.mipmap.ic_launcher
            )
        )
        list.add(
            Model(
                "http://peterdiag.eu",
                "27/3/2021 pm 12:54",
                R.mipmap.ic_launcher_round
            )
        )
        list.add(
            Model(
                "John Doe",
                "27/3/2021 pm 12:54",
                R.mipmap.ic_launcher
            )
        )
        list.add(
            Model(
                "Peter Diag",
                "27/3/2021 pm 12:54",
                R.mipmap.ic_launcher_round
            )
        )
        list.add(
            Model(
                "0950 886 171",
                "27/3/2021 pm 12:54",
                R.mipmap.ic_launcher
            )
        )
        list.add(
            Model(
                "0950 886 171",
                "27/3/2021 pm 12:54",
                R.mipmap.ic_launcher
            )
        )
        list.add(
            Model(
                "0950 886 171",
                "27/3/2021 pm 12:54",
                R.mipmap.ic_launcher
            )
        )
        listView.adapter = ListAdapter(
            requireActivity(),
            R.layout.row,
            list
        )
        return view
    }

}