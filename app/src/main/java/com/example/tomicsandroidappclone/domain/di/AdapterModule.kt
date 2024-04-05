package com.example.tomicsandroidappclone.domain.di

import com.example.tomicsandroidappclone.domain.repository.AdapterRepository
import com.example.tomicsandroidappclone.domain.repository.AdapterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Singleton


@Module
@InstallIn(ActivityComponent::class)
abstract class AdapterModule {

    @Binds
    abstract fun bindingAdapterRepository(adapterImpl: AdapterRepositoryImpl) : AdapterRepository
}