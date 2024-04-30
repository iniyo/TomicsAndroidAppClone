package com.example.tomicsandroidappclone.domain.di

import com.example.tomicsandroidappclone.BuildConfig
import com.example.tomicsandroidappclone.data.api.WebtoonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// NetworkModule.kt
// Binds랑 Provides 어노테이션은 같이 쓰면 안됨.

// 외부 라이브러리에서 제공하는 클래스라 프로젝트 내에서 소유할 수 없는 경우(inject 주입이 안되는 경우)
// 혹은 Builder 패턴 등으로 인스턴스를 생성해야 하는 경우, 종속성 객체를 생성, 제공하는 메서드를 정의할 때 사용

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
            .baseUrl(BuildConfig.WEBTOON_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideWebtoonApi(retrofit: Retrofit): WebtoonApi {
        // Retrofit 인스턴스를 사용하여 WebtoonApi 인터페이스를 구현하는 객체를 생성
        return retrofit.create(WebtoonApi::class.java)
    }
}