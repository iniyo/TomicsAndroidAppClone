package com.example.tomicsandroidappclone.data.database.converter

import androidx.room.TypeConverter
import com.example.tomicsandroidappclone.domain.model.Additional
import com.example.tomicsandroidappclone.domain.model.Tag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EntityConverter {
    @TypeConverter
    fun listToJson(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        return Gson().fromJson(value,Array<String>::class.java)?.toList()
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