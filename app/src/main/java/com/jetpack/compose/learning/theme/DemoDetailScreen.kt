package com.jetpack.compose.learning.theme

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.jetpack.compose.learning.BuildConfig
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.list.advancelist.model.Movie

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DemoDetailScreen(movie: Movie) {
    val scrollState = rememberScrollState()
    val expand = remember { mutableStateOf(false) }
    val painter = rememberImagePainter(
        data = BuildConfig.IMAGE_URL_MOVIEDB + movie.poster_path,
        builder = {
            crossfade(true)
            placeholder(R.drawable.ic_movie_placeholder)
        }
    )

    when (painter.state) {
        is ImagePainter.State.Success -> expand.value = true
        else -> expand.value = false
    }
    Column(
        modifier = Modifier
            .padding(animateDpAsState(if (expand.value) 0.dp else 120.dp, tween(350)).value)
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier
                .height(600.dp)
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.requiredHeight(10.dp))
        Column(
            modifier = Modifier.padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = movie.original_title,
                style = MaterialTheme.typography.h4,
            )
            Spacer(modifier = Modifier.requiredHeight(5.dp))
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.medium
            ) {
                Text(
                    text = "Action, Adventure, Sci-Fi",
                    style = MaterialTheme.typography.body2,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.requiredHeight(5.dp))
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = movie.overview,
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(modifier = Modifier.requiredHeight(10.dp))
            FlowRow(mainAxisSpacing = 16.dp, mainAxisAlignment = FlowMainAxisAlignment.Center) {
                MovieChip(text = movie.release_date.split("-").first())
                MovieChip(text = "13+")
                MovieChip(text = movie.vote_average) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "",
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                }
                MovieChip(text = "2h 30min") {
                    Icon(
                        Icons.Filled.AccessTime,
                        contentDescription = "",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.requiredHeight(10.dp))
        }
    }
}