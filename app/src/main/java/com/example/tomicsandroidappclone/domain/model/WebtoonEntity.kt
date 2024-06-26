package com.example.tomicsandroidappclone.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "toon_images")
data class ToonImage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageResId: Int,
    val category: String
)

data class ToonResponse(
    val totalWebtoonCount: Int,
    val naverWebtoonCount: Int,
    val kakaoWebtoonCount: Int,
    val kakaoPageWebtoonCount: Int,
    val updatedWebtoonCount: Int,
    val createdWebtoonCount: Int,
    val lastUpdate: String,
    val webtoons: ArrayList<Webtoon>
)

@Entity(tableName = "webtoons")
data class Webtoon(
    @PrimaryKey val id: String,
    val webtoonId: Long,
    val title: String,
    var rank: Int,
    val author: String,
    val url: String,
    val img: String,
    val service: String,
    val updateDays: List<String>,
    val fanCount: Int?,
    val searchKeyword: String,
    val additional: Additional,
    // 이후로는 추가
    val tagList: Tag?,
    val category: String
)

data class Additional(
    val new: Boolean, // 신규 웹툰
    val rest: Boolean, // 휴재
    val up: Boolean, // 신규회차 업로드
    val adult: Boolean, // 성인
    val singularityList: List<Any> // 어떤 타입의 객체도 포함 가능한 리스트
)

data class singularityList(
    val over15: String, // 15세 이상
    val free: String, // 완전 무료
    val waitFree: String, // 기다리면 무료
)

data class Tag(
    var tag1: String,
    var tag2: String,
    var tag3: String
)

