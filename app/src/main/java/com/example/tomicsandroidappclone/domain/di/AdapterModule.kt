package com.example.tomicsandroidappclone.domain.di

import com.example.tomicsandroidappclone.presentation.util.adapter.MyEasyAdapter
import com.example.tomicsandroidappclone.presentation.util.adapter.MyEasyAdapterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
abstract class AdapterModule {

    @Binds
    abstract fun bindingAdapterRepository(adapterImpl: MyEasyAdapterImpl): MyEasyAdapter
}