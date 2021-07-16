package com.example.paxclient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.paxclient.aidl.IPaxServerRemoteService
import com.example.paxclient.di.modules.PePLinkerModule
import com.pep.models.AppPid
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

    private val _uiEventsApp = MutableLiveData<AppPid>()
    val uiEventsApp: LiveData<AppPid> = _uiEventsApp

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

    fun getAppPid() {
        pepLinkerDisposable?.dispose()
        Log.d(TAG, "getAppPid")
        pepLinkerDisposable =
            RxPePLinkerConnection.bind(pepLinker, IPaxServerRemoteService::class.java)
                .flatMap { it.getAppPid() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(
                    {
                        _uiEventsApp.value = it
                        Log.d(TAG, "PePLinker getAppPid callback: $it")
                        pepLinkerDisposable?.dispose()
                    }, {
                        Log.d(TAG, "PepLinker getAppPid error: $it")
                        pepLinkerDisposable?.dispose()
                    }
                )
    }
}