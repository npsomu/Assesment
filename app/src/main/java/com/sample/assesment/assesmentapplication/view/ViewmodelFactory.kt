package com.sample.assesment.assesmentapplication.view

import android.os.Build
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.assesment.assesmentapplication.viewmodel.MainViewModel


class ViewmodelFactory(mApplication: MainActivity, mExtra: String) : ViewModelProvider.Factory{

    private var mApplication: MainActivity = mApplication
    private var mExtra: String =mExtra

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(mApplication, mExtra) as T
    }

}