package com.example.paxclient.aidl

import java.util.*
import io.reactivex.Observable
import pl.pep.peplinker.annotation.RemoteInterface

@RemoteInterface
interface IPaxServiceRemoteService {
    fun getPid(): Observable<Int>
}