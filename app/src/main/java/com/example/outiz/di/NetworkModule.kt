package com.example.outiz.di

import android.content.Context
import com.example.outiz.network.ConnectivityInterceptor
import com.example.outiz.network.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkHandler(@ApplicationContext context: Context) = NetworkHandler(context)

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(
        networkHandler: NetworkHandler
    ) = ConnectivityInterceptor(networkHandler)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        connectivityInterceptor: ConnectivityInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(connectivityInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
}