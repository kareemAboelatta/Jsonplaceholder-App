package com.example.androidtaskbosta.data.remote

import com.example.androidtaskbosta.data.models.AlbumApiModel
import com.example.androidtaskbosta.data.models.PhotoApiModel
import com.example.androidtaskbosta.data.models.UserApiModel
import com.example.androidtaskbosta.domain.models.Album
import com.example.androidtaskbosta.domain.models.Photo
import com.example.androidtaskbosta.domain.models.User
import retrofit2.http.GET
import retrofit2.http.Query



interface ApiPlaceholderService {

    @GET("users")
    suspend fun getUsers(): List<UserApiModel>

    @GET("albums")
    suspend fun getAlbumsByUserId(
        @Query("userId") userId: Int
    ): List<AlbumApiModel>

    @GET("photos")
    suspend fun getPhotosByAlbumId(
        @Query("albumId") albumId: Int
    ): List<PhotoApiModel>

}
