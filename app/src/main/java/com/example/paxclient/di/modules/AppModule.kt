package com.example.paxclient.di.modules

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideResources(context: Context): Resources = context.resources

    @Provides
    @Singleton
    fun provideAssets(context: Context): AssetManager = context.assets

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}