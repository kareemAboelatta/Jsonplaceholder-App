package com.example.androidtaskbosta.presentaion.ui.image_viewer


import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageGalleryViewer(
    imageUrls: List<String>,
    initialIndex: Int = 0
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
                onBack = { /* Handle back navigation, if needed */ },
                onShare = { shareImageUrl(context, imageUrls[page]) }
            )
        }

        // Display arrows only if necessary
        if (pagerState.currentPage > 0) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Previous Image",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(48.dp)
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                    .padding(16.dp),
                tint = Color.White
            )
        }

        if (pagerState.currentPage < imageUrls.size - 1) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next Image",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(48.dp)
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                    .padding(16.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
fun ImageViewer(
    imageUrl: String,
    onBack: () -> Unit,
    onShare: () -> Unit
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
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                )
                .fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        // Top app bar with back and share icons
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
                    .clickable { onBack() },
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onShare() },
                tint = Color.White
            )
        }
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

