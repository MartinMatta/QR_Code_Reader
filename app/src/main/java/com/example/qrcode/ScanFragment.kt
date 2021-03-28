package com.example.qrcode

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.CodeScanner

class ScanFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //activity?.title = "Scan"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_scan, container, false)
        val buttonScan: Button = view.findViewById(R.id.buttonScan)

        buttonScan.setOnClickListener {
            val fragment = ScanCodeFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frame, fragment)
                ?.commit();
        }

        return view
    }
}