package com.example.tomicsandroidappclone.domain.di

import android.app.Application
import android.content.Context
import com.example.tomicsandroidappclone.BuildConfig
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.data.api.WebtoonApi
import com.example.tomicsandroidappclone.presentation.util.navigator.AppNavigator
import com.example.tomicsandroidappclone.presentation.util.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(ActivityComponent::class) // Activity 수준에서 사용되는 것을 뜻함
@Module
abstract class NavigationModule {

    @Binds //AppNavigator 인터페이스를 Impl 구현체에 연결하는 역할
    abstract fun bindNavigtor(impl: AppNavigatorImpl): AppNavigator
}

