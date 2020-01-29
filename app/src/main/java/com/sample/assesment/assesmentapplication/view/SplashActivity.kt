package com.sample.assesment.assesmentapplication.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sample.assesment.assesmentapplication.R
import com.sample.assesment.assesmentapplication.data.common.AppConstant
import com.sample.assesment.assesmentapplication.data.common.Keys

class SplashActivity : AppCompatActivity(){

    private val splashTimeout = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(Runnable {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },splashTimeout)
    }
}