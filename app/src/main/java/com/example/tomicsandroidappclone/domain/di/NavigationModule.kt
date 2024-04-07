package com.example.tomicsandroidappclone.domain.di

import com.example.tomicsandroidappclone.presentation.util.navigator.AppNavigator
import com.example.tomicsandroidappclone.presentation.util.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class) // Activity 수준에서 사용되는 것을 뜻함
@Module
abstract class NavigationModule {

    @Binds //AppNavigator 인터페이스를 Impl 구현체에 연결하는 역할
    abstract fun bindNavigtor(impl: AppNavigatorImpl): AppNavigator
}
