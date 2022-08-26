package com.refreshing.datalayer.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.refreshing.datalayer.apis

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideDefaultSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(appContext)
    }

    @Provides
    @Singleton
    fun setupOkHttp(
        @ApplicationContext appContext: Context,
        sharedPreferences: SharedPreferences
    ): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val preferences = PreferenceManager.getDefaultSharedPreferences(appContext)


        return OkHttpClient.Builder()

            .connectTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(Interceptors(sharedPreferences,preferences.getString("lang", "ar").toString()))



            .build()
    }




    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://munesh-corner.com/")

            .addConverterFactory(gsonConverterFactory).build()
    }


    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }



    @Provides
    fun getUserServices(retrofit: Retrofit): apis {
        return retrofit.create(apis::class.java)
    }

}