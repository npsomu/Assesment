package com.sample.assesment.assesmentapplication.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView

class Utils {

    companion object {
        @SuppressLint("MissingPermission")
        fun isNetworkConnected(context: Context): Boolean {

            var connectionManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectionManager.activeNetworkInfo

            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }

        fun viewSize(context: Context, imageView: ImageView): Int {

            //Get actual width and height of image
            val width: Int = imageView.width
            val height: Int = imageView.height

            // Calculate the ratio between height and width of Original Image
            val ratio : Float = height.toFloat() / width.toFloat()
            val scale : Float = context.resources.displayMetrics.density
            return ((width * ratio) / scale).toInt()

        }
    }

}