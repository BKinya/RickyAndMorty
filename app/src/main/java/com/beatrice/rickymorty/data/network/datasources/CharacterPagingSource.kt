package com.beatrice.rickymorty.data.network.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.beatrice.rickymorty.data.network.CharacterApiService
import com.beatrice.rickymorty.data.network.util.NO_INTERNET
import com.beatrice.rickymorty.data.network.util.SERVER_ERROR_TAG
import com.beatrice.rickymorty.data.network.util.safeApiRequest
import com.beatrice.rickymorty.data.util.toDomain
import com.beatrice.rickymorty.domain.model.Character
import logcat.logcat
import okio.IOException


private const val STARTING_PAGE_INDEX = 1
class CharacterPagingSource(
    private val apiService: CharacterApiService
): PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page: Int = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiService.getCharacters(page)
            LoadResult.Page(
                data = response.characterInfos.toDomain(),
                nextKey = page + 1,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            )
        }catch (exception: IOException){
            logcat{
                "$NO_INTERNET with ${exception.message}"
            }
            LoadResult.Error(exception)
        }
        catch (e: Exception){
            logcat{
                "$SERVER_ERROR_TAG with ${e.message}"
            }
            LoadResult.Error(e)
        }
    }
}
