package com.example.tomicsandroidappclone.data.database

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tomicsandroidappclone.data.api.WebtoonApi
import com.example.tomicsandroidappclone.domain.model.Webtoon
import javax.inject.Inject

// 참고 사이트 : https://leveloper.tistory.com/202
class WebtoonPagingSource @Inject constructor(private val service: WebtoonApi) : PagingSource<Int, Webtoon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Webtoon> {
        Log.d("TAG", "load data ")
        return try {
            val currentPage = params.key ?: 1 // 첫 페이지는 1로 시작
            val response = service.getWebtoon(
                currentPage,
                params.loadSize,
                "kakao",
                "mon"
            )
            LoadResult.Page(
                data = response.webtoons, // API 응답에서 웹툰 데이터 리스트 추출
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.webtoons.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
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