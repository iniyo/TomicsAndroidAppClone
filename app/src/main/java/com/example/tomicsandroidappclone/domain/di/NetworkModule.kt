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
// -> provides는 특정 타입의 instance를 생성하는 방법을 알려주므로 동일한 타입의 객체가 생성될 때 provides에서 정의한 대로 생성 됨.

// 외부 라이브러리에서 제공하는 클래스라 프로젝트 내에서 소유할 수 없는 경우(inject 주입이 안되는 경우)
// 혹은 Builder 패턴 등으로 인스턴스를 생성해야 하는 경우, 종속성 객체를 생성, 제공하는 메서드를 정의할 때 사용

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        // HTTP 캐시 사용
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(File(context.cacheDir, "http"), cacheSize.toLong()) // 캐시 객체 생성 시
    }

    // HTTP 캐시 설정
    @Singleton
    @Provides
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        // Cache-Control - HTTP 헤더에 캐시 제어를 할 수 있는 지시문을 담는 필드
        val maxAge = Integer.MAX_VALUE
        return OkHttpClient.Builder()
            .cache(cache)
            // 요청이 서버로 보내지기 전에 가로채서 요청을 수정.
            .addInterceptor { chain ->
                var request = chain.request()
                // 해당 요청 지정한 만큼 캐싱하겠다는 것을 서버측에 알림. -> 앱 수준
                request = request.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
                chain.proceed(request) // interceptor를 2개 사용하므로, 순차적으로 인터셉터 호출을 위해 사용.
            }
            // 서버로부터 받은 응답의 헤더를 수정하여 클라이언트 캐시 제어 -> 네트워크 수준
            // 별도 제어 없이 자동으로 캐시제어 가능.
            .addNetworkInterceptor { chain ->
                val response = chain.proceed(chain.request())
                // Customize or return the response
                response.newBuilder()
                    .removeHeader("Pragma") // Pragma -> HTTP/1.0 에서 사용되는 캐시 제어 헤더, request, response 캐싱을 하지 않게 만듦.
                    .header("Cache-Control", "public, max-age=$maxAge")
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