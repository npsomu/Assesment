package com.sample.assesment.assesmentapplication.data.remote

import com.sample.assesment.assesmentapplication.data.model.Facts
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService{

    @GET("/{apikey}/facts.json")
    fun getFacts (@Path("apikey") key : String) : Deferred<Response<Facts>>
}