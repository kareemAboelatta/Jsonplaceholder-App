package com.example.androidtaskbosta.presentaion.ui.image_viewer


import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.androidtaskbosta.R
import com.example.androidtaskbosta.presentaion.ui.album_details.AlbumDetailsViewModel
import com.example.common.ui.utils.PaddingDimensions
import kotlinx.coroutines.launch


@Composable
fun ImageViewPage(
    navController: NavHostController,
    viewModel:  AlbumDetailsViewModel,
    initialIndex: Int = 0
) {
    val imageUrls = viewModel.filteredPhotos.collectAsState().value.map { it.url }
    ImageGalleryViewer(
        imageUrls = imageUrls,
        initialIndex = initialIndex,
        navController = navController
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageGalleryViewer(
    imageUrls: List<String>,
    initialIndex: Int = 0,
    navController: NavHostController,


    ) {
    val pagerState = rememberPagerState(
        pageCount = { imageUrls.size }, initialPage = initialIndex,
    )
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            ImageViewer(
                imageUrl = imageUrls[page],
            )
        }
        ProgressIndicators(
            total = imageUrls.size,
            current = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopStart),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        //back
                        navController.popBackStack()

                    },
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        shareImageUrl(context, imageUrl = imageUrls[pagerState.currentPage])
                    },
                tint = Color.White
            )
        }


        // Display arrows only if necessary
        if (pagerState.currentPage > 0) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Previous Image",
                modifier = Modifier
                    .padding(PaddingDimensions.small)
                    .background(
                        color = Color.White.copy(alpha = 0.4f),
                        shape = CircleShape
                    )
                    .align(Alignment.CenterStart)
                    .size(48.dp)
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                    .padding(PaddingDimensions.small),
                tint = Color.White
            )
        }

        if (pagerState.currentPage < imageUrls.size - 1) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next Image",
                modifier = Modifier
                    .padding(PaddingDimensions.small)
                    .background(
                        color = Color.White.copy(alpha = 0.4f),
                        shape = CircleShape
                    )
                    .align(Alignment.CenterEnd)
                    .size(48.dp)
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                    .padding(PaddingDimensions.small),
                tint = Color.White
            )
        }
    }
}

@Composable
fun ProgressIndicators(total: Int, current: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(total) { index ->
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == current) Color.White
                        else Color.Gray
                    )
            )
        }
    }
}


@Composable
fun ImageViewer(
    imageUrl: String,
) {
    var scale by remember { mutableFloatStateOf(1f) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pinchToZoomGestureFilter { scale *= it }
            .background(Color.Black)
    ) {
        AsyncImage(
            imageUrl,
            contentDescription = null,
            error = painterResource(R.drawable.ic_broken_image),

            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                )
                .fillMaxSize(),
            contentScale = ContentScale.Fit
        )


    }
}

fun Modifier.pinchToZoomGestureFilter(onScale: (Float) -> Unit): Modifier = composed {
    val scaleState = rememberUpdatedState(onScale)
    val gestureState = remember { PinchToZoomGestureState() }

    this.pointerInput(Unit) {
        detectTransformGestures { _, pan, zoom, _ ->
            scaleState.value.invoke(zoom)
            gestureState.currentZoom *= zoom
            gestureState.offset = gestureState.offset + pan / gestureState.currentZoom
        }
    }
}

private class PinchToZoomGestureState {
    var currentZoom: Float = 1f
    var offset: Offset = Offset(0f, 0f)
}

fun shareImageUrl(context: Context, imageUrl: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, imageUrl)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(sendIntent, null))
}

