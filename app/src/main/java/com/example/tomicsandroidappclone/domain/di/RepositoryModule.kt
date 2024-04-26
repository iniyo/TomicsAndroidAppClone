package com.example.tomicsandroidappclone.domain.di

import com.example.tomicsandroidappclone.data.repository.WebtoonRepositoryImpl
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



// RepositoryModule.kt
@Module
@InstallIn(SingletonComponent::class) // 이 모듈에서 제공 될 객체의 범위를 제한
abstract class RepositoryModule {

    @Binds // 인터페이스와 인터페이스 구현체를 연결하는 데 사용
    @Singleton // 싱글톤임을 나타냄, 즉, 어플리케이션이 실행되는 동안 단일 인스턴스만 존재.
    // !중요 - 여기서 :는 상속이 아닌 바인딩된 관계를 뜻함.
    // 즉, 아래 함수는 다른 클래스에서 webtoonInterface(webtoonRepository)를 요청하면
    // 구현체인 webtoonRepositoryImpl의 인스턴스를 제공함.
    abstract fun bindWebtoonRepository( WebtoonImpl: WebtoonRepositoryImpl ): WebtoonRepository
}
