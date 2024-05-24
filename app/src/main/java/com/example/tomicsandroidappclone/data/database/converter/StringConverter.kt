package com.example.tomicsandroidappclone.data.database.converter

import androidx.room.TypeConverter
import com.example.tomicsandroidappclone.domain.model.Additional
import com.example.tomicsandroidappclone.domain.model.Tag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Android room은 무려 컬럼에 사용자 정의 DataClass나 List를 사용하면 에러가 발생한다.
// 기본 자료형과 wrapping type만 지원하기 때문.
// why? UI 스레드에서 디스크에 관한 정보를 쿼리하면 상당한 성능 문제가 발생.
// 따라서 converter를 달아주어 변환을 명시해주어야 한다.
class EntityConverter {
    @TypeConverter
    fun listToJson(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        return Gson().fromJson(value, Array<String>::class.java)?.toList()
    }

    @TypeConverter
    fun fromAdditional(additional: Additional?): String? {
        return Gson().toJson(additional)
    }

    @TypeConverter
    fun toAdditional(additionalString: String?): Additional? {
        return Gson().fromJson(additionalString, object : TypeToken<Additional>() {}.type)
    }

    @TypeConverter
    fun fromTag(additional: Tag?): String? {
        return Gson().toJson(additional)
    }

    @TypeConverter
    fun toTag(tagString: String?): Tag? {
        return Gson().fromJson(tagString, object : TypeToken<Tag>() {}.type)
    }
}