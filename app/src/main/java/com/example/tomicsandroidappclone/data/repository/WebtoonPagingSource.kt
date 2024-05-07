package com.example.tomicsandroidappclone.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tomicsandroidappclone.data.remote.api.WebtoonApi
import com.example.tomicsandroidappclone.domain.model.Webtoon
import com.example.tomicsandroidappclone.presentation.util.mapper.MyCalendar

// 참고 사이트 : https://leveloper.tistory.com/202
class WebtoonPagingSource (
    private val api: WebtoonApi,
    private var toDay: String? = null,
    private var genres: String? = null,
    private var populatiry: String? = null,
    private var endToon: String? = null,
    private var hot: String? = null,
) : PagingSource<Int, Webtoon>() {

    fun dataSetting(today: String, genre: String, popularity: String, endtoon: String, hott: String) {
        toDay = today
        genres = genre
        populatiry = popularity
        endToon = endtoon
        hot = hott
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Webtoon> {
        Log.d("TAG", "load data ")
        return try {
            val mCalendar = MyCalendar().invoke()
            val currentPage = params.key ?: 0 // 첫 페이지 0dm로 시작
            val paramSize = params.loadSize
            Log.d("TAG", "load:$toDay")
            val response = when {
                toDay != null && toDay != "" && toDay != "new" -> api.getTodayWebtoon(currentPage, paramSize, "kakao", toDay!!)
                toDay == "new" -> api.getAllWebtoon(0,0,"kakao") // 모든 웹툰 가져오기
                else -> api.getTodayWebtoon(currentPage, paramSize, "kakao", mCalendar)
            }
            val webtoons = if (toDay == "new") {
                // "new"가 true인 웹툰만 필터링
                response.webtoons.filter { it.additional.new }
            } else {
                response.webtoons
            }
            LoadResult.Page(
                data = webtoons, // API 응답에서 웹툰 데이터 리스트 추출
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (response.webtoons.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            Log.e("TAG", "load:${e.message} ")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Webtoon>): Int? {
        // 최초 로딩 시 첫 페이지를 새로고침 키로 설정
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}