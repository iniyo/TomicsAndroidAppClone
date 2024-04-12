package com.example.tomicsandroidappclone.data.repository

import com.example.tomicsandroidappclone.data.api.WebtoonApi
import com.example.tomicsandroidappclone.domain.entity.ToonResponse
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton // For Hilt
class WebtoonRepositoryImpl @Inject constructor(private val api: WebtoonApi) : WebtoonRepository {
    override suspend fun getWebtoon(
        perPage: Int,
        page: Int,
        service: String,
        updateDay: String
    ): ToonResponse {
        return api.getWebtoon(perPage, page, service, updateDay)
    }
    override suspend fun getDayByWebtoons(service: String, updateDay: String): ArrayList<Webtoon> {
        return api.getWebtoon(0, 0, service, updateDay).webtoons
    }

    override suspend fun getKeywordByWebtoons(keyword: String): ToonResponse {
        return api.getSearch(keyword)
    }


    /*override fun getWebtoon(perPage: Int, page: Int, service: String, updateDay: String): ToonResponse? {
        var result: ToonResponse? = null
       *//* val response = api.getWebtoon(perPage, page, service, updateDay).execute()*//*

        api.getWebtoon(perPage, page, service, updateDay).enqueue(object : Callback<ToonResponse> {
            override fun onResponse(call: Call<ToonResponse>, response: Response<ToonResponse>) {
                if (response.isSuccessful) {
                    result = response.body()
                } else {
                    throw Exception("API 호출 실패: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ToonResponse>, t: Throwable) {
                throw Exception("API 호출 실패: $t")
            }
        })
        return result
    }

    override fun getDayByWebtoons(service: String, updateDay: String): ToonResponse {
        return getWebtoon(0, 0, service, updateDay)
    }

    override fun getKeywordByWebtoons(keyword: String): Call<ToonResponse> {
        return api.getSearch(keyword)
    }*/

}
