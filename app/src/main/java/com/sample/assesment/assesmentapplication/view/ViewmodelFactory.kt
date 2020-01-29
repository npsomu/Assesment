package com.sample.assesment.assesmentapplication.view

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.assesment.assesmentapplication.viewmodel.MainViewModel


class ViewmodelFactory(mApplication: Application, mExtra: String) : ViewModelProvider.Factory{

    private var mApplication: Application = mApplication
    private var mExtra: String =mExtra

    @NonNull
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(mApplication, mExtra) as T
    }

}