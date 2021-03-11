package com.example.testapi.data.remote

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testapi.data.RickMortyApi
import com.example.testapi.data.remote.model.CharacterEntity
import com.example.testapi.util.CharacterFilterCapsule

class CharacterPagingSource(
    var characterFilter: CharacterFilterCapsule,
    val movieApiService: RickMortyApi,
) : PagingSource<Int, CharacterEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        val pageNumber = params.key ?: 1
        try {
            val filter = characterFilter.getFilter()
            val response =
                movieApiService.getCharacter(pageNumber, filter.name, filter.species, filter.status)
            val pagedResponse = response
            val data = pagedResponse.results

            val nextPageNumber: Int?
            val uri = Uri.parse(pagedResponse.info.next)
            val nextPageQuery = uri.getQueryParameter("page")
            nextPageNumber = nextPageQuery?.toInt()
            return LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int {
        return 1
    }
}