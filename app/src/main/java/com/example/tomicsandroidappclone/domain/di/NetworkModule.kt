package com.example.tomicsandroidappclone.domain.di

import android.content.Context
import com.example.tomicsandroidappclone.BuildConfig
import com.example.tomicsandroidappclone.data.network.WebtoonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

// NetworkModule.kt
// Binds랑 Provides 어노테이션은 같이 쓰면 안됨.

// 외부 라이브러리에서 제공하는 클래스라 프로젝트 내에서 소유할 수 없는 경우(inject 주입이 안되는 경우)
// 혹은 Builder 패턴 등으로 인스턴스를 생성해야 하는 경우, 종속성 객체를 생성, 제공하는 메서드를 정의할 때 사용

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(File(context.cacheDir, "http"), cacheSize.toLong())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                // 5 seconds cache duration
                request = request.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 5)
                    .build()
                chain.proceed(request)
            }
            .addNetworkInterceptor { chain ->
                val response = chain.proceed(chain.request())
                // Customize or return the response
                response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + 5)
                    .build()
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.WEBTOON_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideWebtoonApi(retrofit: Retrofit): WebtoonApi {
        return retrofit.create(WebtoonApi::class.java)
    }
}