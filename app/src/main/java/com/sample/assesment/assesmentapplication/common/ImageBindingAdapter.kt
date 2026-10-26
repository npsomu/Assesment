package com.sample.assesment.assesmentapplication.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sample.assesment.assesmentapplication.R

object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageUrl(view : ImageView, url:String){
        Glide.with(view)
            .load(url)
            .override(view.width,Utils.viewSize(view.context,view))
            .placeholder(R.drawable.ic_no_image)
            .into(view)
    }
}


