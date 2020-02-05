package com.sample.assesment.assesmentapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.assesment.assesmentapplication.R
import com.sample.assesment.assesmentapplication.data.model.Facts
import com.sample.assesment.assesmentapplication.data.remote.ApiClient
import kotlinx.coroutines.*
import retrofit2.Response

class MainViewModel(val mApplication: Application, val mApiKey: String) : ViewModel(){

    val mFactsData : MutableLiveData<Facts> by lazy {
        MutableLiveData<Facts>().also{
            loadDataList(mApiKey)
        }
    }
    val error = MutableLiveData<String>()

    fun loadDataList(mApiKey: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                withContext(Dispatchers.IO) {
                    // Dispatchers.Main
                    var resultDef: Deferred<Response<Facts>> = getDataFromServer(mApiKey)
                    try {
                        var result: Response<Facts> = resultDef.await()
                        if (result.isSuccessful) {
                            var resposne = result.body()
                            resposne?.let {
                                mFactsData.postValue(resposne)
                            }
                        } else {
                            // response not get
                            error.postValue(mApplication.getString(R.string.error))
                            Log.e("MainViewModel else", "error")
                        }
                    } catch (ex: Exception) {
                        error.postValue(ex.message)
                        resultDef.getCompletionExceptionOrNull()?.let {
                            println(resultDef.getCompletionExceptionOrNull()!!.message)
                        }

                    }
                }
            }catch (e:Throwable){
                error.postValue(mApplication.getString(R.string.error))
            }
        }
    }
    private suspend fun getDataFromServer(mApiKey: String) = withContext(Dispatchers.IO) {
        ApiClient.mApiService.getFacts(mApiKey)
    }

}