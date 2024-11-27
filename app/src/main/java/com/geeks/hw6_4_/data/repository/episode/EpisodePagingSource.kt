package com.geeks.hw6_4_.data.repository.episode

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.geeks.hw6_4_.data.model.EpisodeResponse
import com.geeks.hw6_4_.data.network.EpisodeApiService

class EpisodePagingSource(
    private val episodeApiService: EpisodeApiService
) : PagingSource<Int, EpisodeResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeResponse> {
        return try {
            val currentPage = params.key ?: 1

            val response = episodeApiService.getAllEpisodes(currentPage)
            val episodes = response.episodeResponse ?: emptyList()

            val nextPage = if (response.pageInfo?.next == null) {
                null
            } else {
                Uri.parse(response.pageInfo.next).getQueryParameter("page")?.toInt()
            }
            LoadResult.Page(
                data = episodes,
                nextKey = nextPage,
                prevKey = if (currentPage == 1) null else currentPage - 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodeResponse>): Int? {
        return state.anchorPosition
    }
}