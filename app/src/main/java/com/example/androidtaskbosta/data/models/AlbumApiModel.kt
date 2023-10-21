package com.example.androidtaskbosta.data.models

import com.example.androidtaskbosta.domain.models.Album

data class AlbumApiModel(
    val id: Int,
    val userId: Int,
    val title: String
)


fun AlbumApiModel.mapToAlbum(): Album = Album(
    id=  id,userId= userId , title = title
)

