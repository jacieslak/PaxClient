package com.example.paxclient.aidl

import java.util.*
import io.reactivex.Observable
import pl.pep.peplinker.annotation.RemoteInterface

@RemoteInterface
interface IPaxServerRemoteService {
    fun getPid(): Observable<Int>
}