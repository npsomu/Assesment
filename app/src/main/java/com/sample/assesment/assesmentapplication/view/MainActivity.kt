package com.sample.assesment.assesmentapplication.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sample.assesment.assesmentapplication.R
import com.sample.assesment.assesmentapplication.data.common.AppConstant
import com.sample.assesment.assesmentapplication.data.model.Facts
import com.sample.assesment.assesmentapplication.databinding.ActivityMainBinding
import com.sample.assesment.assesmentapplication.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recylerview_row_layout.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel : MainViewModel
    private lateinit var binding : ActivityMainBinding
    var recyclerViewAdapter : RecyclerViewAdapter? = null

    companion object{
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ViewmodelFactory(application,AppConstant.APIKEY)
        mainViewModel = ViewModelProviders.of(this,factory).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
        binding.modeldata = mainViewModel
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateForOrientation(newConfig.orientation)
    }

    private fun updateForOrientation(orientation: Int) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            showSystemUI()
        }
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_VISIBLE
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (SYSTEM_UI_FLAG_LAYOUT_STABLE
                or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                or SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    private fun initView() {
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)
        swipe_container.setOnRefreshListener {
            getDataFromServer()
            swipe_container.isRefreshing = false
        }
        mainViewModel.mFactsData.observe(this, Observer<Facts> {Facts -> updateList(Facts)  })
        mainViewModel.error.observe(
             this, Observer<String> {
                showError(it)
            }
        )
    }

    private fun showError(msg: String) {
        Snackbar.make(contraint_layout,msg,Snackbar.LENGTH_SHORT).show()
    }

    private fun getDataFromServer() {
        mainViewModel.loadDataList(AppConstant.APIKEY)
    }

    private fun updateList(facts: Facts) {
        title_text.text = facts.title
        recyclerViewAdapter = RecyclerViewAdapter(applicationContext,facts.rows)
        recyclerview.adapter = recyclerViewAdapter
    }
}
