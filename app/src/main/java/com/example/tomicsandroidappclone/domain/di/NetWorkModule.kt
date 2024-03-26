package com.example.tomicsandroidappclone.domain.di

import com.example.tomicsandroidappclone.data.api.WebtoonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// NetworkModule.kt
// Binds랑 Provides 어노테이션은 같이 쓰면 안됨.
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // @Provides 어노테이션은 provideRetrofit 함수가 Retrofit 인스턴스를 제공한다는 것을 나타냄.
    // @Singleton 어노테이션은 Retrofit 인스턴스가 싱글톤으로 생성되어야 한다는 것을 나타냄.
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        // Retrofit 빌더를 사용하여 Retrofit 인스턴스를 생성
        // 빌더에 기본 URL, JSON 변환달아줌
        return Retrofit.Builder()
            .baseUrl("https://korea-webtoon-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // @Provides 어노테이션은 provideWebtoonApi 함수가 WebtoonApi 인스턴스를 제공한다는 것을 나타냄
    // @Singleton 어노테이션은 WebtoonApi 인스턴스가 싱글톤으로 생성되어야 한다는 것을 나타냄
    @Singleton
    @Provides
    fun provideWebtoonApi(retrofit: Retrofit): WebtoonApi {
        // Retrofit 인스턴스를 사용하여 WebtoonApi 인터페이스를 구현하는 객체를 생성
        return retrofit.create(WebtoonApi::class.java)
    }
}