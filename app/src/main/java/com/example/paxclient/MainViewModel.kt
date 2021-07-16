package com.example.paxclient

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.paxclient.aidl.IPaxServiceRemoteService
import com.example.paxclient.di.modules.PePLinkerModule
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import pl.pep.peplinker.PePLinker
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pl.pep.peplinker.rxbinder.RxPePLinkerConnection


@HiltViewModel
class MainViewModel @Inject constructor(
    @PePLinkerModule.PaxClientPepLinker private val pepLinker: PePLinker,
): ViewModel() {

    private val TAG = "MainViewModel"
    private var pepLinkerDisposable: Disposable? = null

    fun getPid() {
        pepLinkerDisposable?.dispose()
        pepLinkerDisposable =
            RxPePLinkerConnection.bind(pepLinker, IPaxServiceRemoteService::class.java)
                .flatMap { it.getPid() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(
                    {
                        Log.d(TAG, "PePLinker callback: $it")
                        pepLinkerDisposable?.dispose()
                    }, {
                        Log.d(TAG, "PepLinker error: $it")
                        pepLinkerDisposable?.dispose()
                    }
                )
    }
}