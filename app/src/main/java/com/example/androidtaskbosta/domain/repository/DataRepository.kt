package com.example.androidtaskbosta.domain.repository

import com.example.androidtaskbosta.domain.models.Album
import com.example.androidtaskbosta.domain.models.Photo
import com.example.androidtaskbosta.domain.models.User

interface  DataRepository {

    suspend fun fetchUsers(): List<User>
    suspend fun fetchAlbumsByUserId(userId: Int): List<Album>
    suspend fun fetchPhotosByAlbumId(albumId: Int): List<Photo>
}