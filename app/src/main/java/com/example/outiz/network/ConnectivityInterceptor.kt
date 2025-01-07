package com.example.outiz.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class ConnectivityInterceptor @Inject constructor(
    private val networkHandler: NetworkHandler
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkHandler.isNetworkAvailable()) {
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }
}

class NoConnectivityException : IOException() {
    override val message: String
        get() = "Aucune connexion internet disponible"
}