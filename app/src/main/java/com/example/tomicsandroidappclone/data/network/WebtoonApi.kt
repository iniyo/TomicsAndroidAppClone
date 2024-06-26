package com.example.tomicsandroidappclone.data.network

import com.example.tomicsandroidappclone.domain.model.ToonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebtoonApi {

    // 비동기적으로 처리할 것.
    @GET("/")
    suspend fun getTodayWebtoon(
        @Query("perPage") perPage: Int,
        @Query("page") page: Int,
        @Query("service") service: String, // naver, kakao, kakaoPage
        @Query("updateDay") updateDay: String // mon, tue, wed, thu, fri, sat, sun, finished, naverDaily
    ): ToonResponse

    @GET("/")
    suspend fun getAllWebtoon(
        @Query("perPage") perPage: Int,
        @Query("page") page: Int,
        @Query("service") service: String, // naver, kakao, kakaoPage
    ): ToonResponse

    @GET("/search")
    suspend fun getSearch(
        @Query("keyword") keyword: String
    ): ToonResponse
}