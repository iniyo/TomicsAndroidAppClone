package com.example.tomicsandroidappclone.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tomicsandroidappclone.data.database.ImageData
import com.example.tomicsandroidappclone.data.database.WebtoonDao
import com.example.tomicsandroidappclone.domain.model.ToonImage
import com.example.tomicsandroidappclone.domain.model.Webtoon
import com.example.tomicsandroidappclone.domain.usecase.GetToonByDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val getToonByDayUseCase: GetToonByDayUseCase,
    private val webtoonDao: WebtoonDao  // DAO 주입
) : ViewModel() {
    private val _pagingData = MutableStateFlow<Flow<PagingData<Webtoon>>?>(null)
    val pagingData: StateFlow<Flow<PagingData<Webtoon>>?> = _pagingData.asStateFlow()

    private val _webtoonsInfo = MutableLiveData<ArrayList<Webtoon>>()
    val webtoonsInfo: LiveData<ArrayList<Webtoon>> = _webtoonsInfo

    init {
        Log.d("TAG", "BaseViewModel - init ")
        // api data - not paging
        fetchWebtoons()
        // room data
        clearAndInsertInitialAdImages()
    }

    /**
     * room database
     */
    // LiveData to hold the images
    private val _topAdImages = MutableLiveData<List<ToonImage>>()
    val topAdImages: LiveData<List<ToonImage>> = _topAdImages

    private val _popularityToonImages = MutableLiveData<List<ToonImage>>()
    val popularityToonImages: LiveData<List<ToonImage>> = _popularityToonImages

    // 기존 데이터를 삭제하고, 새로운 데이터를 삽입하는 함수
    private fun clearAndInsertInitialAdImages() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // 모든 카테고리의 데이터를 삭제
                webtoonDao.deleteAllImages()

                // 고유한 이미지 리소스 ID를 추적하는 집합
                val uniqueImageMap = mutableMapOf<Pair<Int, String>, ToonImage>()

                // 새로운 데이터 삽입
                ImageData.categoryImagesMap.forEach { (category, images) ->
                    images.forEach { imageResId ->
                        val toonImage = ToonImage(imageResId = imageResId, category = category)
                        uniqueImageMap[Pair(imageResId, category)] = toonImage
                    }
                }

                // 중복 제거 후 삽입
                val uniqueImages = uniqueImageMap.values.toList()
                webtoonDao.insertAll(uniqueImages)
                Log.d("TAG", "Inserted ${uniqueImages.size} unique images")
            } catch (e: Exception) {
                Log.e("TAG", "clearAndInsertInitialAdImages error: ${e.message}")
            }
        }
    }

    // 특정 카테고리의 이미지 데이터를 불러오는 함수
    fun loadAdImages(category: String) {
        viewModelScope.launch {
            try {
                val images = withContext(Dispatchers.IO) {
                    webtoonDao.getImagesByCategory(category)
                }.distinctBy { it.imageResId } // 중복 이미지 제거

                Log.d("TAG", "Loaded ${images.size} images for category $category")
                when (category) {
                    "topAdImages" -> _topAdImages.postValue(images)
                    "popularityToonImages" -> _popularityToonImages.postValue(images)
                }
            } catch (e: Exception) {
                Log.e("TAG", "loadAdImages error: ${e.message}")
            }
        }
    }

    /**
     *
     */

    // Main은 메인스레드에서 실행, UI 관련 작업에 사용 됨
    // IO는 I/O관련 - 네트워크 서비스나 디스크 I/O작업
    fun getSelectDayWebtoon(today: String) {
        viewModelScope.launch {
            try {
                val webtoonsFlow = getToonByDayUseCase.getUserSelectDayToonData(today).cachedIn(viewModelScope)
                withContext(Dispatchers.Main) {
                    _pagingData.value = webtoonsFlow
                }
            } catch (e: Exception) {
                Log.e("TAG", "getSelectDayWebtoon error: ${e.message}")
            }
        }
    }

    private fun fetchWebtoons() {
        Log.d("TAG", "fetchWebtoons 실행 : ")
        viewModelScope.launch {
            val toonResponseResult = getToonByDayUseCase
            withContext(Dispatchers.Main) {
                _webtoonsInfo.value = toonResponseResult()
            }
        }
    }
}
