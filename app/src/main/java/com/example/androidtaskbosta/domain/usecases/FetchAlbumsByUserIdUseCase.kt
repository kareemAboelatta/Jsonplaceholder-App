package com.example.androidtaskbosta.domain.usecases

import com.example.androidtaskbosta.domain.models.Album
import com.example.androidtaskbosta.domain.repository.DataRepository
import javax.inject.Inject


class FetchAlbumsByUserIdUseCase @Inject constructor(private val dataRepository: DataRepository)  {
    suspend fun execute(userId: Int): List<Album> {
        return dataRepository.fetchAlbumsByUserId(userId)
    }
}


