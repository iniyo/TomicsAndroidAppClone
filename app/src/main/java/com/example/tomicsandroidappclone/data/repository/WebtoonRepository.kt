package com.example.tomicsandroidappclone.data.repository
import com.example.tomicsandroidappclone.domain.entity.ToonResponse
import com.example.tomicsandroidappclone.domain.entity.Webtoon


interface WebtoonRepository  {
    suspend fun getWebtoon(perPage: Int, page: Int, service: String, updateDay: String): ToonResponse
    suspend fun getDayByWebtoons(service: String, updateDay: String): ArrayList<Webtoon>
    suspend fun getKeywordByWebtoons(keyword: String): ToonResponse
}

