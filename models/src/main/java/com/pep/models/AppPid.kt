package com.pep.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppPid(val appName: String?, val pid: Int) : Parcelable {
}