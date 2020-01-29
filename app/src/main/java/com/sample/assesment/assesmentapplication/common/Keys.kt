package com.sample.assesment.assesmentapplication.data.common

class Keys{
    companion object {
        init {
            System.loadLibrary("key-lib")
        }
    }
    external fun apikey(): String
}