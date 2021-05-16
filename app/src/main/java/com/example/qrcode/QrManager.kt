package com.example.qrcode

import android.os.Parcel
import android.os.Parcelable

class QrManager() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QrManager> {
        override fun createFromParcel(parcel: Parcel): QrManager {
            return QrManager(parcel)
        }

        override fun newArray(size: Int): Array<QrManager?> {
            return arrayOfNulls(size)
        }
    }
}