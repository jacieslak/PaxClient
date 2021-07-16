package com.example.paxclient.aidl

import android.os.Parcel
import android.os.Parcelable

data class AppPid(val appName: String?, val pid: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<AppPid> {
        override fun createFromParcel(parcel: Parcel): AppPid {
            return AppPid(parcel)
        }

        override fun newArray(size: Int): Array<AppPid?> {
            return arrayOfNulls(size)
        }
    }

}