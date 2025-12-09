package com.beatrice.rickymorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.beatrice.rickymorty.data.network.CharacterApiService
import com.beatrice.rickymorty.data.network.datasources.CharacterPagingSource
import com.beatrice.rickymorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

private const val PAGE_SIZE = 30

class CharacterRepository(
    private val apiService: CharacterApiService
) {
    fun getAllCharacters(): Flow<PagingData<Character>> {
        val response = Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = true
            ),
            pagingSourceFactory = { CharacterPagingSource(apiService) }

        ).flow

        return response
    }
}
