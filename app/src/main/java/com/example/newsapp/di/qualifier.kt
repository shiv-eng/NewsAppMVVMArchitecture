package com.example.newsapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DatabaseName

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class NetworkAPIKey

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl
