package com.refreshing.datalayer.di

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson

import okhttp3.Interceptor
import okhttp3.Response

class Interceptors(
    private val sharedPreferences: SharedPreferences,
    private val lang:String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        request = request.newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("lang",lang)
            .build()
        return chain.proceed(request)
    }


}