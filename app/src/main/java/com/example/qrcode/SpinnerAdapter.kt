package com.example.qrcode

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class CustomSpinnerAdapter(ctx: Context, countries: ArrayList<SpinnerData>) : ArrayAdapter<SpinnerData>(ctx, 0, countries) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent);
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent);
    }

    fun createItemView(position: Int, recycledView: View?, parent: ViewGroup):View {
        val country = getItem(position)

        return recycledView ?: LayoutInflater.from(context).inflate(
                R.layout.spinner_row,
                parent,
                false
        )
    }


}

enum class OperatedQR(val codeType: String) {
    URL("URL"),
    TEXT("TEXT"),
    CONTACT("CONTACT"),
    EMAIL("EMAIL"),
    SMS_ADDRESS("SMS"),
    GPS("GPS"),
    TEL_NUMBER("TEL"),
    CALENDAR("CALENDAR"),
    WIFI("WIFI"),
}