package com.sample.assesment.assesmentapplication.data.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sample.assesment.assesmentapplication.data.common.AppConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient{


    private val myOkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(getHttpLoggingInterceptor())
        .build()


    fun retrofit() : Retrofit = Retrofit.Builder()
        .client(myOkHttpClient)
        .baseUrl(AppConstant.BASE_URL) // YOUR BASE URL OF API
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    private fun getHttpLoggingInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }


    val mApiService : ApiService = retrofit().create(ApiService::class.java)

}