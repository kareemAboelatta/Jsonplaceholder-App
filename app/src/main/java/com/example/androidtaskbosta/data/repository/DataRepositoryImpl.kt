package com.example.androidtaskbosta.data.repository

import com.example.androidtaskbosta.data.models.mapToAlbum
import com.example.androidtaskbosta.data.models.mapToPhoto
import com.example.androidtaskbosta.data.models.mapToUser
import com.example.androidtaskbosta.data.remote.ApiPlaceholderService
import com.example.androidtaskbosta.domain.repository.DataRepository

import com.example.androidtaskbosta.domain.models.Album
import com.example.androidtaskbosta.domain.models.Photo
import com.example.androidtaskbosta.domain.models.User



class DataRepositoryImpl(private val apiService: ApiPlaceholderService) : DataRepository {
    override suspend fun fetchUsers(): List<User> {
        return apiService.getUsers().map { it.mapToUser() }
    }

    override suspend fun fetchAlbumsByUserId(userId: Int): List<Album> {
        return apiService.getAlbumsByUserId(userId).map { it.mapToAlbum() }
    }

    override suspend fun fetchPhotosByAlbumId(albumId: Int): List<Photo> {
        return apiService.getPhotosByAlbumId(albumId).map { it.mapToPhoto() }
    }
}