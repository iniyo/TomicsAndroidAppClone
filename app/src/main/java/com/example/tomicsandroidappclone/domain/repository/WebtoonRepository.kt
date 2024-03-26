package com.example.tomicsandroidappclone.domain.repository
import com.example.tomicsandroidappclone.domain.entity.ToonResponse


interface WebtoonRepository  {
    suspend fun getWebtoon(page: Int, perPage: Int, service: String, updateDay: String): ToonResponse
    /*suspend fun getWebtoons(): ToonResponse*/
}

