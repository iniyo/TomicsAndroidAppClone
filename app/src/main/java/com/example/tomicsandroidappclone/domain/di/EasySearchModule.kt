package com.example.tomicsandroidappclone.domain.di

import android.app.Application
import android.content.Context
import com.example.tomicsandroidappclone.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object EasySearchModule {
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
    @Provides
    fun provideStringArray(@ApplicationContext context: Context): Array<String> {
        return context.resources.getStringArray(R.array.free_webtoon_tab_items)
    }
}
