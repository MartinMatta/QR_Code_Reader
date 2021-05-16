package com.example.qrcode

import android.os.Parcel
import android.os.Parcelable

class QrResult() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QrResult> {
        override fun createFromParcel(parcel: Parcel): QrResult {
            return QrResult(parcel)
        }

        override fun newArray(size: Int): Array<QrResult?> {
            return arrayOfNulls(size)
        }
    }
}