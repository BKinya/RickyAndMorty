package com.beatrice.rickymorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.beatrice.rickymorty.data.network.CharacterApiService
import com.beatrice.rickymorty.data.network.datasources.CharacterPagingSource
import com.beatrice.rickymorty.domain.model.Character
import com.beatrice.rickymorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

private const val PAGE_SIZE = 30
class CharacterRepositoryImpl(
    private val apiService: CharacterApiService
) : CharacterRepository {
    override fun getAllCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = true,
            ),
            pagingSourceFactory = { CharacterPagingSource(apiService) }
        ).flow
    }
}
