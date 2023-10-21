package com.example.androidtaskbosta.presentaion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidtaskbosta.presentaion.theme.AndroidTaskBostaTheme
import com.example.androidtaskbosta.presentaion.ui.album_details.AlbumDetailsScreen
import com.example.androidtaskbosta.presentaion.ui.album_details.AlbumDetailsViewModel
import com.example.androidtaskbosta.presentaion.ui.image_viewer.ImageViewPage
import com.example.androidtaskbosta.presentaion.ui.profile.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTaskBostaTheme {
                val navController = rememberNavController()
                val albumDetailsViewModel: AlbumDetailsViewModel = hiltViewModel()



                NavHost(navController = navController, startDestination = Screen.Profile.route) {


                    composable(Screen.Profile.route) {
                        ProfileScreen(navController = navController, viewModel = hiltViewModel())
                    }

                    composable(
                        "albumDetails/{albumId}",
                        arguments = listOf(navArgument("albumId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        backStackEntry.arguments?.getInt("albumId")?.let {
                            AlbumDetailsScreen(
                                navController = navController,
                                albumId = it,
                                viewModel = albumDetailsViewModel
                            )
                        }
                    }

                    composable(
                        "imageView/{initialIndex}"
                    ) { backStackEntry ->
                        val initialIndex = backStackEntry.arguments?.getInt("initialIndex") ?: 0
                        ImageViewPage(
                            navController = navController,
                            viewModel = albumDetailsViewModel,
                            initialIndex = initialIndex
                        )
                    }
                }

            }
        }
    }
}


sealed class Screen(val route: String) {
    object Profile : Screen("profile")

    class AlbumDetails(val albumId: Int) : Screen("albumDetails/{albumId}") {
        companion object {
            fun route(albumId: Int) = "albumDetails/$albumId"
        }
    }

    data class ImageView(val imageUrls: List<String>, val initialIndex: Int) :
        Screen("imageView/{initialIndex}") {
        companion object {
            fun route(initialIndex: Int) = "imageView/$initialIndex"
        }
    }
}





