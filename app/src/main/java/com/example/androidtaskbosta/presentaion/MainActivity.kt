package com.example.androidtaskbosta.presentaion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidtaskbosta.domain.repository.DataRepository
import com.example.androidtaskbosta.presentaion.theme.AndroidTaskBostaTheme
import com.example.androidtaskbosta.presentaion.ui.album_details.AlbumDetailsPagePreview
import com.example.androidtaskbosta.presentaion.ui.album_details.AlbumDetailsScreen
import com.example.androidtaskbosta.presentaion.ui.image_viewer.ImageGalleryViewer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AndroidTaskBostaTheme {
                ImageGalleryViewer(
                    imageUrls = listOf(
                        "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?auto=format&fit=crop&q=80",
                        "https://media.istockphoto.com/id/1146517111/photo/taj-mahal-mausoleum-in-agra.jpg?s=612x612&w=0&k=20&c=vcIjhwUrNyjoKbGbAQ5sOcEzDUgOfCsm9ySmJ8gNeRk=",
                        "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?auto=format&fit=crop&q=80",
                        "https://thatshelf.com/wp-content/uploads/2019/06/Spider-Man-Far-From-Home-Featured.jpg",
                        "https://images.unsplash.com/photo-1575936123452-b67c3203c357?auto=format&fit=crop&q=80",
                        "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?auto=format&fit=crop&q=80",
                    ),
                )
            }
        }
    }
}

