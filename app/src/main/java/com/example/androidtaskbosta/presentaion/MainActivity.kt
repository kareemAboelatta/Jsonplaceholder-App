package com.example.androidtaskbosta.presentaion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidtaskbosta.domain.repository.DataRepository
import com.example.androidtaskbosta.presentaion.theme.AndroidTaskBostaTheme
import com.example.androidtaskbosta.presentaion.ui.album_details.AlbumDetailsPagePreview
import com.example.androidtaskbosta.presentaion.ui.album_details.AlbumDetailsScreen
import com.example.androidtaskbosta.presentaion.ui.profile.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AndroidTaskBostaTheme {
                AlbumDetailsScreen(1)
            }
        }
    }
}

