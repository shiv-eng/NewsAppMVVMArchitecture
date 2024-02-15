package com.example.newsapp.utils

class TestNetworkHelper : NetworkHelper {
    override fun isNetworkConnected(): Boolean {
        return true
    }
}