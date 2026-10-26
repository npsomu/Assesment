package com.sample.assesment.assesmentapplication.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.sample.assesment.assesmentapplication.R
import com.sample.assesment.assesmentapplication.common.NetworkCallBack
import com.sample.assesment.assesmentapplication.data.model.Facts
import com.sample.assesment.assesmentapplication.data.remote.ApiClient
import com.sample.assesment.assesmentapplication.view.MainActivity
import kotlinx.coroutines.*
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MainViewModel(private val mApplication: MainActivity, private val mApiKey: String) : ViewModel(){

    private val connectionLiveData : LiveData<Boolean>

    init {
        connectionLiveData = NetworkCallBack(mApplication)
    }

    val mFactsData : MutableLiveData<Facts> by lazy {
        MutableLiveData<Facts>().also{
            connectionLiveData.observe(mApplication, Observer <Boolean>{
                if(it == true){
                    loadDataList(mApiKey)
                }else{
                    error.postValue(mApplication.getString(R.string.error))
                }
            })
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

