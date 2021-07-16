package com.example.paxclient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.paxclient.aidl.IPaxServerRemoteService
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

    private val _uiEvents = MutableLiveData<Int>()
    val uiEvents: LiveData<Int> = _uiEvents

    private val TAG = "MainViewModel"
    private var pepLinkerDisposable: Disposable? = null

    fun getPid() {
        pepLinkerDisposable?.dispose()
        Log.d(TAG, "getPid")
        pepLinkerDisposable =
            RxPePLinkerConnection.bind(pepLinker, IPaxServerRemoteService::class.java)
                .flatMap { it.getPid() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(
                    {
                        _uiEvents.value = it
                        Log.d(TAG, "PePLinker callback: $it")
                        pepLinkerDisposable?.dispose()
                    }, {
                        Log.d(TAG, "PepLinker error: $it")
                        pepLinkerDisposable?.dispose()
                    }
                )
    }
}