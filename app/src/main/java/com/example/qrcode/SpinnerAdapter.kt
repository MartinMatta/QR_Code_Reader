package com.example.qrcode

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class CustomSpinnerAdapter(ctx: Context, countries: ArrayList<CountryData>) : ArrayAdapter<CountryData>(ctx, 0, countries) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent);
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent);
    }

    fun createItemView(position: Int, recycledView: View?, parent: ViewGroup):View {
        val country = getItem(position)

        val view = recycledView ?: LayoutInflater.from(context).inflate(
                R.layout.spinner_row,
                parent,
                false
        )

        country?.let {
            view..setImageResource(country.flag)
            view.textViewCountryName.text = country.countryName
        }
        return view
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