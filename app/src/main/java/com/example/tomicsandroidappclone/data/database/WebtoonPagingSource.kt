package com.example.tomicsandroidappclone.data.database

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tomicsandroidappclone.data.api.WebtoonApi
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import javax.inject.Inject

// 참고 사이트 : https://leveloper.tistory.com/202
class WebtoonPagingSource @Inject constructor(private val service: WebtoonApi) :
    PagingSource<Int, Webtoon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Webtoon> {
        return try {
            val page = params.key ?: 1 // 시작 페이지 번호 (기본값: 1)
            val pageSize = params.loadSize // 한 페이지당 로드할 데이터 수
            val response = service.getWebtoon(
                perPage = pageSize,
                page = page,
                service = "kakao",
                updateDay = "mon"
            ) // Webtoon 목록 API 호출
            LoadResult.Page(response.webtoons, page + 1, null)

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