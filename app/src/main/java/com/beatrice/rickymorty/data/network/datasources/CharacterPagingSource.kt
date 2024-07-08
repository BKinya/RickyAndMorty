package com.beatrice.rickymorty.data.network.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.beatrice.rickymorty.data.network.CharacterApiService
import com.beatrice.rickymorty.data.util.toDomain
import com.beatrice.rickymorty.domain.model.Character


private const val STARTING_PAGE_INDEX = 1
class CharacterPagingSource(
    private val apiService: CharacterApiService
): PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page: Int = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiService.getCharacters(page = page)
            LoadResult.Page(
                data = response.characterInfos.toDomain(),
                nextKey = page + 1,
                prevKey = page - 1
            )
        }catch (exception: Exception){
            LoadResult.Error(exception)
        }
    }
}
