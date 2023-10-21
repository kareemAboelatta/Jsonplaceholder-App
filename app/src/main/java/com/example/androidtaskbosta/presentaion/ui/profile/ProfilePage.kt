package com.example.androidtaskbosta.presentaion.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.androidtaskbosta.domain.models.Album
import com.example.androidtaskbosta.domain.models.User
import com.example.androidtaskbosta.presentaion.Screen
import com.example.common.ui.utils.PaddingDimensions
import com.example.common.utils.CustomError
import com.example.common.utils.UIState
import com.example.common.utils.getDisplayMessage


@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val userState = viewModel.userState.value
    val albumState = viewModel.albumState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ProfileDetailsSection(userState = userState, onRetry = viewModel::fetchUsers)
        Spacer(modifier = Modifier.height(16.dp))
        AlbumListSection(albumState = albumState, navController = navController)
    }
}

@Composable
fun ProfileDetailsSection(
    userState: UIState<User>,
    onRetry: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    when (userState) {
        is UIState.Success -> {
            val user = userState.data
            // Fetch albums whenever user.id changes
            LaunchedEffect(key1 = user.id) {
                viewModel.fetchAlbums(user.id)
            }
            Text(text = user.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "${user.address.city}, ${user.address.street}")
        }

        is UIState.Loading -> CentralizedProgressIndicator()
        is UIState.Error -> CentralizedErrorView(error = userState.error, onRetry = onRetry)
        UIState.Empty -> CentralizedTextView(text = "No user data available!")
    }
}

@Composable
fun AlbumListSection(
    navController: NavHostController,
    albumState: UIState<List<Album>>
) {

    when (albumState) {
        is UIState.Success -> {
            val albums = albumState.data
            LazyColumn {
                items(albums) { album ->
                    AlbumItem(
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.AlbumDetails.route(album.id))
                        },
                        album = album
                    )
                }
            }
        }

        is UIState.Loading -> CentralizedProgressIndicator()
        is UIState.Error -> CentralizedErrorView(error = albumState.error)
        UIState.Empty -> CentralizedTextView(text = "No albums available!")
    }
}

@Composable
fun CentralizedProgressIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CentralizedErrorView(error: CustomError, onRetry: () -> Unit = {}) {
    val errorMessage = error.getDisplayMessage() // Assuming you added an extension on CustomError
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun CentralizedTextView(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}


@Composable
fun AlbumItem(
    album: Album,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = album.title)
        Spacer(modifier = Modifier.height(8.dp))
        Divider(Modifier.height(PaddingDimensions.mini))
    }
}

























