package com.sample.assesment.assesmentapplication.common

import android.content.Context
import android.widget.ImageView

class Utils {

    companion object {

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