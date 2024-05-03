package com.example.tomicsandroidappclone.domain.di

import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import com.example.tomicsandroidappclone.domain.usecase.GetToonByDayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


// use case
// 사용해서 얻는 이점
// 1. 여러 view model에서 데이터 로직 중복 없이 데이터 공유 가능
// 2. view model간 공유하는 usecase의 생명주기를 activity에 맞게 매핑가능 -> ActivityRetainedScoped. 내 경우 baseActivity 생명주기를 따르게 됨.
// 3. view model간 하위 레이어를 통해 데이터 공유 가능. -> LiveData, SheardPreference등 사용하지 않고 하위 레이어인 repository를 가져와 사용 가능.
// 4. View Model이 굳이 View를 통해서 상위/하위에 데이터를 전달하지 않기 -> 상/하위 view model과 결합되지 않고 use case로 하위 레이어로부터 데이터 가져와 전달 가능.

// 참고 사이트1 : https://medium.com/@wodbs135/view-lifecycle%EC%97%90-%EB%94%B0%EB%9D%BC-viewmodel-%EA%B0%84-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EA%B3%B5%EC%9C%A0%ED%95%98%EA%B8%B0-c6d9dbf2682b
// 참고 사이트2 : https://velog.io/@cchloe2311/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-UseCase%EB%A5%BC-%EC%99%9C-%EC%93%B0%EB%82%98%EC%9A%94

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    // 현재는 리포지토리 module과 다를 것 없음. 단, Activity나 Fragment에서 사용하는 데이터를 공유하고 싶을때 사용 가능.
    // 얼핏 repository와 비슷해 보이지만, use case는 필요한 데이터를 통해 비즈니스 로직을 적용하는 역할.
    // repository는 데이터를 가져오는 창구 역할
    @Provides
    @ActivityRetainedScoped
    fun provideGetSharedDataUseCase(
        webtoonRepository: WebtoonRepository
    ) = GetToonByDayUseCase(
        webtoonRepository = webtoonRepository
    )
}
