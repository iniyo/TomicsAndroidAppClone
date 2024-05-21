package com.example.tomicsandroidappclone.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tomicsandroidappclone.data.remote.api.WebtoonApi
import com.example.tomicsandroidappclone.domain.model.Webtoon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WebtoonPagingSource(
    private val api: WebtoonApi,
    private var category: String? = null
) : PagingSource<Int, Webtoon>() {

    private val seenWebtoons = HashSet<String>()

    fun dataSetting(category: String) {
        this.category = category
        seenWebtoons.clear() // 카테고리 변경 시 중복 웹툰 초기화
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Webtoon> {
        Log.d("TAG", "load data ")
        return try {
            val currentPage = params.key ?: 0
            val paramSize = params.loadSize

            // 비동기적으로 네트워크 데이터 로딩
            val response = withContext(Dispatchers.IO) {
                when (category) {
                    "new" -> api.getAllWebtoon(currentPage, paramSize, "kakao")
                    else -> api.getTodayWebtoon(currentPage, paramSize, "kakao", category!!)
                }
            }

            val webtoons = response.webtoons
                .filter { it.img.isNotEmpty() }
                .distinctBy { it.webtoonId }
                .filter { seenWebtoons.add(it.webtoonId.toString()) } // HashSet에 추가되지 않은 항목만 추가

            val endOfPaginationReached = webtoons.isEmpty()

            LoadResult.Page(
                data = webtoons,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (endOfPaginationReached) null else currentPage + 1
            )
        } catch (e: Exception) {
            Log.e("TAG", "load:${e.message} ")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Webtoon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
