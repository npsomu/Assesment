package com.sample.assesment.assesmentapplication.data.model

import com.google.auto.value.AutoValue

@AutoValue
abstract class CountryInfoItem {

    companion object {
        fun create( description: String,
                    imageHref: String,
                    title: String): CountryInfoItem {
            return create(description, imageHref, title)
        }
    }

    abstract fun description(): String
    abstract fun imageHref(): String
    abstract fun title(): String
}