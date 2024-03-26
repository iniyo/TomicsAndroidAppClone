package com.example.tomicsandroidappclone.domain.entity

data class ToonResponse(
    val totalWebtoonCount: Int,
    val naverWebtoonCount: Int,
    val kakaoWebtoonCount: Int,
    val kakaoPageWebtoonCount: Int,
    val updatedWebtoonCount: Int,
    val createdWebtoonCount: Int,
    val lastUpdate: String,
    val webtoons: List<Webtoon>
)

data class Webtoon(
    val _id: String,
    val webtoonId: Long,
    val title: String,
    val author: String,
    val url: String,
    val img: String,
    val service: String,
    val updateDays: List<String>,
    val fanCount: Int?,
    val searchKeyword: String,
    val additional: Additional
)

data class Additional(
    val new: Boolean,
    val rest: Boolean,
    val up: Boolean,
    val adult: Boolean,
    val singularityList: List<Any> // 어떤 타입의 객체도 포함 가능한 리스트
)
