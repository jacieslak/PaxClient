package com.example.paxclient.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.pep.peplinker.PePLinker
import pl.pep.peplinker.adapter.OriginalCallAdapterFactory
import pl.pep.peplinker.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PePLinkerModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PaxClientPepLinker

    @Provides
    @Singleton
    @PaxClientPepLinker
    fun provideLauncherPePLinker(
        context: Context
    ): PePLinker = PePLinker.Builder(context)
        .packageName(PAXSERVICE_REMOTE_SERVICE_PKG)
        .action(PAXSERVICE_REMOTE_SERVICE_ACTION) // Specify the callback executor by yourself
        .addCallAdapterFactory(OriginalCallAdapterFactory.create()) // Basic
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava2
        .build()

    companion object {
        const val PAXSERVICE_REMOTE_SERVICE_PKG = "com.pep.paxserver"
        const val PAXSERVICE_REMOTE_SERVICE_ACTION = "com.pep.paxserver.PAX_SERVER_REMOTE_SERVICE_ACTION"
    }
}

