package com.example.androidtaskbosta.data.models

import com.example.androidtaskbosta.domain.models.Album
import com.example.androidtaskbosta.domain.models.Photo

data class PhotoApiModel(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)


fun PhotoApiModel.mapToPhoto(): Photo = Photo(
    id=  id,albumId= albumId , title = title, url = url, thumbnailUrl = thumbnailUrl,
)