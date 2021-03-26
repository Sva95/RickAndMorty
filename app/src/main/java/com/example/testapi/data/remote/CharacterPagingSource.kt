package com.example.testapi.data.remote

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testapi.data.RickMortyApi
import com.example.testapi.data.local.CharacterDao
import com.example.testapi.data.local.toDb
import com.example.testapi.data.local.toDomain
import com.example.testapi.data.prefs.CharacterSharedPrefs
import com.example.testapi.data.remote.model.CharacterApi
import com.example.testapi.domain.model.CharacterEntity
import com.example.testapi.util.CharacterFilterCapsule

class CharacterPagingSource(
    private var characterFilter: CharacterFilterCapsule,
    private val movieApiService: RickMortyApi,
    private val characterDao: CharacterDao,
    private val characterPrefs: CharacterSharedPrefs
) : PagingSource<Int, CharacterEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        val pageNumber = params.key ?: 1
        try {
            val filter = characterFilter.getFilter()
            var characters = characterDao.getCharactersOnPage(
                pageNumber,
                filter.name,
                filter.status,
                filter.species
            ).map { it.toDomain() }

            if (characters.isEmpty()) {
                val characterResponse =
                    movieApiService.getCharacter(
                        pageNumber,
                        filter.name,
                        filter.species,
                        filter.status
                    )
                characterPrefs.setMaxPages(characterResponse.info.pages)
                characterDao.insertAll(characterResponse.results.map { it.toDb() })
                characters = characterResponse.results.map { it.toDomain() }
            }

            return LoadResult.Page(
                data = characters,
                prevKey = null,
                nextKey = if (characterPrefs.getMaxPages() > pageNumber) pageNumber + 1 else null
            )

/*            val response = characterService.getCharacters(pageNumber)

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
            )*/

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int? {
        return state.anchorPosition
    }
}