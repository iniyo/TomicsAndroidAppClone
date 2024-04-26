package com.example.tomicsandroidappclone.domain.di

import com.example.tomicsandroidappclone.presentation.util.adapter.MyEasyAdapter
import com.example.tomicsandroidappclone.presentation.util.adapter.MyEasyAdapterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.internal.QualifierMetadata
import javax.inject.Qualifier

// 이거 붙이면 impl은 다르고 같은 이름의 클래스 사용할 때 앞에 붙여서 구분할 수 있음.
@Qualifier
annotation class EasyAdapter
@Qualifier
annotation class NotEasyAdapter

@Module
@InstallIn(ActivityComponent::class)
abstract class AdapterModule {


    @EasyAdapter
    @Binds
    abstract fun bindingAdapterRepository(adapterImpl: MyEasyAdapterImpl): MyEasyAdapter
}