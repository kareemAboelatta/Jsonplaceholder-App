package com.example.androidtaskbosta.domain.usecases

import com.example.androidtaskbosta.domain.models.Photo
import com.example.androidtaskbosta.domain.repository.DataRepository
import javax.inject.Inject

class FetchPhotosByAlbumIdUseCase  @Inject constructor( private val dataRepository: DataRepository)  {
    suspend fun execute(albumId: Int): List<Photo> {
        return dataRepository.fetchPhotosByAlbumId(albumId)
    }
}