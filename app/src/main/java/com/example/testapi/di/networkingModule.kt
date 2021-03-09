package com.example.testapi.di


import com.example.testapi.BuildConfig
import com.example.testapi.data.RickMortyApi
import com.example.testapi.util.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single { okhttpClient() }
    single { retrofit(okHttpClient = get()) }
    single { apiService(retrofit = get()) }
}

fun apiService(
    retrofit: Retrofit
): RickMortyApi =
    retrofit.create(RickMortyApi::class.java)

val logging = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}

fun retrofit(
    okHttpClient: OkHttpClient
): Retrofit =
    Retrofit.Builder()
        .baseUrl(Constant.RICK_AND_MORTY_URL_API)
        .client(okHttpClient)
       // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun okhttpClient(
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
