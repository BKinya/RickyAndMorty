package com.beatrice.rickymorty.data.network.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.beatrice.rickymorty.data.network.CharacterApiService
import com.beatrice.rickymorty.data.network.util.GENERAL_SERVER_ERROR
import com.beatrice.rickymorty.data.network.util.NO_INTERNET
import com.beatrice.rickymorty.data.util.toDomain
import com.beatrice.rickymorty.domain.model.Character
import logcat.logcat
import okio.IOException

private const val STARTING_PAGE_INDEX = 1
class CharacterPagingSource(
    private val apiService: CharacterApiService
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            val anchorPage = state.closestPageToPosition(anchorPosition = anchorPos)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page: Int = params.key ?: STARTING_PAGE_INDEX
        return try {
            val characters = apiService.getCharacters(page = page).results
            LoadResult.Page(
                data = characters.toDomain(),
                nextKey = if (characters.isEmpty()) null else page + 1,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            )
        } catch (exc: IOException) {
            logcat { "$NO_INTERNET => ${exc.message}" }
            val exception = IOException(NO_INTERNET)
            LoadResult.Error(exception)
        } catch (exc: Exception) {
            logcat { "$GENERAL_SERVER_ERROR => ${exc.message}" }
            val exception = Exception(GENERAL_SERVER_ERROR)
            LoadResult.Error(exception)
        }
    }
}
