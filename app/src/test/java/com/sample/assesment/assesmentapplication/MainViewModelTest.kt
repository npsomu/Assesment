package com.sample.assesment.assesmentapplication

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sample.assesment.assesmentapplication.common.NetworkCallBack
import com.sample.assesment.assesmentapplication.data.common.AppConstant
import com.sample.assesment.assesmentapplication.data.model.Facts
import com.sample.assesment.assesmentapplication.data.remote.ApiClient
import com.sample.assesment.assesmentapplication.view.MainActivity
import com.sample.assesment.assesmentapplication.viewmodel.MainViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertThat
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response


@RunWith(JUnit4::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var connectionLiveData: LiveData<Boolean>

    @Mock
    lateinit var mainActivity: MainActivity

    @Mock
    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var appConstant: AppConstant

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
        // Init your view model
//        viewModel = MainViewModel(mainActivity, appConstant.APIKEY)
//        connectionLiveData = NetworkCallBack(mainActivity)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    val mFactsData: MutableLiveData<Facts> by lazy {
        MutableLiveData<Facts>().also {
            connectionLiveData.observe(mainActivity, Observer<Boolean> {
                if (it == true) {
                    loadDataList_WithKey()
                } else {
                    println("else "+mainActivity.getString(R.string.error))
                }
            })
        }
    }

    @Test
    fun loadDataList_WithKey() {

        GlobalScope.launch(Dispatchers.Main) {
            try {
                withContext(Dispatchers.IO) {
                    // Dispatchers.Main
                    var resultDef: Deferred<Response<Facts>> = getDataFromServer(appConstant.APIKEY)
                    try {
                        var result: Response<Facts> = resultDef.await()
                        Log.d("loadDataList_WithKey "," message code :"+result.code())
                        Assert.assertEquals(200,result.code())
                    } catch (ex: Exception) {
                        resultDef.getCompletionExceptionOrNull()?.let {
                            Log.d("loadDataList_WithKey ",resultDef.getCompletionExceptionOrNull()!!.message)
                        }

                    }
                }
            }catch (e:Throwable){
                Log.d("loadDataList_WithKey ","Throwable exception "+e.message)
            }
        }
    }


    @Test
    fun loadDataList_WithoutKey() {

        GlobalScope.launch(Dispatchers.Main) {
            try {
                withContext(Dispatchers.IO) {
                    // Dispatchers.Main
                    var resultDef: Deferred<Response<Facts>> = getDataFromServer("null")
                    try {
                        var result: Response<Facts> = resultDef.await()
                        Log.d("loadDataList_WithoutKey "," message code :"+result.code())
                        Assert.assertEquals(404,result.code())
                    } catch (ex: Exception) {
                        Log.d("loadDataList_WithoutKey ","Exception "+ex.message)
                    }
                }
            }catch (e:Throwable){
                Log.d("loadDataList_WithoutKey ","Throwable exception "+e.message)
            }
        }
    }


    private suspend fun getDataFromServer(mApiKey: String) = withContext(Dispatchers.IO) {
        ApiClient.mApiService.getFacts(mApiKey)
    }

}

