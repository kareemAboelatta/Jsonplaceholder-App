package com.example.androidtaskbosta.data.remote

import com.example.androidtaskbosta.domain.models.Album
import com.example.androidtaskbosta.domain.models.Photo
import com.example.androidtaskbosta.domain.models.User
import retrofit2.http.GET
import retrofit2.http.Query



interface ApiPlaceholderService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("albums")
    suspend fun getAlbumsByUserId(
        @Query("userId") userId: Int
    ): List<Album>

    @GET("photos")
    suspend fun getPhotosByAlbumId(
        @Query("albumId") albumId: Int
    ): List<Photo>

}
