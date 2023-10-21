package com.example.androidtaskbosta.data.repository

import com.example.androidtaskbosta.data.remote.ApiPlaceholderService
import com.example.androidtaskbosta.domain.repository.DataRepository

import com.example.androidtaskbosta.domain.models.Album
import com.example.androidtaskbosta.domain.models.Photo
import com.example.androidtaskbosta.domain.models.User



class DataRepositoryImpl(private val apiService: ApiPlaceholderService) : DataRepository {
    override suspend fun fetchUsers(): List<User> {
        return apiService.getUsers()
    }

    override suspend fun fetchAlbumsByUserId(userId: Int): List<Album> {
        return apiService.getAlbumsByUserId(userId)
    }

    override suspend fun fetchPhotosByAlbumId(albumId: Int): List<Photo> {
        return apiService.getPhotosByAlbumId(albumId)
    }
}