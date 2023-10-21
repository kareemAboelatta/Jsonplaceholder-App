package com.example.androidtaskbosta.presentaion.ui.album_details


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.androidtaskbosta.domain.models.Photo
import com.example.androidtaskbosta.presentaion.Screen
import com.example.androidtaskbosta.presentaion.ui.profile.CentralizedErrorView
import com.example.androidtaskbosta.presentaion.ui.profile.CentralizedProgressIndicator
import com.example.androidtaskbosta.presentaion.ui.profile.CentralizedTextView
import com.example.common.utils.UIState


@Composable
fun AlbumDetailsScreen(
    navController: NavHostController,
    albumId: Int,
    viewModel: AlbumDetailsViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredPhotos by viewModel.filteredPhotos.collectAsState()
    val albumPhotosState by viewModel.albumPhotosState.collectAsState()

    LaunchedEffect(albumId) {
        viewModel.fetchAlbumDetails(albumId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AlbumSearchBar(searchQuery) { newQuery ->
            viewModel.updateSearchQuery(newQuery)
        }
        Spacer(modifier = Modifier.height(16.dp))
        AlbumThumbnailGrid(
            navController = navController,
            filteredPhotos = filteredPhotos,
            state = albumPhotosState,
            onRetry = {
                viewModel.fetchAlbumDetails(albumId)
            })
    }
}

@Composable
fun AlbumThumbnailGrid(
    navController: NavHostController,
    state: UIState<List<Photo>>,
    filteredPhotos: List<Photo>,
    onRetry: () -> Unit = {}
) {
    when (state) {
        is UIState.Loading -> CentralizedProgressIndicator()
        is UIState.Error -> CentralizedErrorView(
            error = state.error,
            onRetry = onRetry
        )

        is UIState.Success -> {
            if (filteredPhotos.isEmpty()) {
                CentralizedTextView(text = "No photos available!")
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    itemsIndexed(filteredPhotos) { index, photo ->
                        Box(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(Screen.ImageView.route(initialIndex = index))
                                }
                                .padding(4.dp)
                                .aspectRatio(1f)
                        ) {
                            AsyncImage(
                                photo.url,
                                contentDescription = "Album Thumbnail",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }

        UIState.Empty -> CentralizedTextView(text = "No photos available!")
    }
}

@Composable
fun AlbumSearchBar(query: String, onQueryChanged: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text(text = "Search in images...") },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.2F), shape = RoundedCornerShape(12.dp)),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}






































































