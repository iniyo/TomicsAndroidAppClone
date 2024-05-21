package com.example.tomicsandroidappclone.presentation.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tomicsandroidappclone.data.database.AppDatabase
import com.example.tomicsandroidappclone.domain.model.ToonImage
import kotlinx.coroutines.launch


// room으로 저장된 내부 이미지 불러오기
class RoomDataViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).webtoonDao()

    fun insertImages(images: List<ToonImage>) {
        viewModelScope.launch {
            dao.insertAll(images)
        }
    }

    fun getImagesByCategory(category: String, onResult: (List<ToonImage>) -> Unit) {
        viewModelScope.launch {
            val images = dao.getImagesByCategory(category)
            onResult(images)
        }
    }
}