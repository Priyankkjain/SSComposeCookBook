package com.jetpack.compose.learning.theme

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.gson.Gson
import com.jetpack.compose.learning.BuildConfig
import com.jetpack.compose.learning.R
import com.jetpack.compose.learning.list.advancelist.model.Movie
import com.jetpack.compose.learning.list.advancelist.uiclass.ErrorMessage
import com.jetpack.compose.learning.list.advancelist.uiclass.LoadingNextPageItem
import com.jetpack.compose.learning.list.advancelist.uiclass.PageLoader
import com.jetpack.compose.learning.list.advancelist.viewmodel.MovieViewModel
import com.jetpack.compose.learning.list.checkForInternet
import kotlinx.coroutines.flow.Flow

@Composable
fun DemoListingScreen(navController: NavController) {
    if (checkForInternet(LocalContext.current)) {
        MovieListScreen(viewModel = MovieViewModel()) {
            val json = Uri.encode(Gson().toJson(it))
            navController.navigate(route = "${DemoNavigationScreenType.Detail.route}/$json")
        }
    } else {
        Toast.makeText(LocalContext.current, R.string.connection_alert, Toast.LENGTH_SHORT)
            .show()
    }
}

@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel,
    onMovieItemClick: (Movie) -> Unit
) {
    MovieInfoWidget(modifier, userList = viewModel.user, onMovieItemClick)
}

@Composable
fun MovieInfoWidget(
    modifier: Modifier,
    userList: Flow<PagingData<Movie>>,
    onMovieItemClick: (Movie) -> Unit
) {
    val userListItems: LazyPagingItems<Movie> = userList.collectAsLazyPagingItems()

    LazyColumn(contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp)) {

        items(userListItems) { item ->
            item?.let {
                MovieItem2(data = it, onClick = {
                    onMovieItemClick(it)
                })
                Spacer(modifier = Modifier.requiredHeight(16.dp))
            }
        }

        userListItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingNextPageItem(modifier) }
                }
                loadState.append is LoadState.Error -> {
                    val error = userListItems.loadState.append as LoadState.Error
                    item {
                        ErrorMessage(modifier = modifier,
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MovieItem2(data: Movie, onClick: () -> Unit) {

    val roundedCornerSize = 10.dp

    val image = rememberImagePainter(
        data = BuildConfig.IMAGE_URL_MOVIEDB + data.poster_path,
        builder = {
            crossfade(true)
            placeholder(R.drawable.ic_movie_placeholder)
        }
    )


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(roundedCornerSize),
        elevation = 5.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .widthIn(max = 120.dp)
                    .aspectRatio(3f / 4)
                    .clip(shape = RoundedCornerShape(roundedCornerSize)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.requiredWidth(10.dp))
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = data.original_title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2, overflow = TextOverflow.Ellipsis,
                )
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.medium
                ) {
                    Text(
                        text = "Action, Adventure, Sci-Fi",
                        style = MaterialTheme.typography.body2,
                        fontSize = 14.sp
                    )
                }
                FlowRow(mainAxisSpacing = 8.dp) {
                    MovieChip(text = "2018")
                    MovieChip(text = "13+")
                    MovieChip(text = data.vote_average) {
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
            }
            Spacer(modifier = Modifier.requiredWidth(10.dp))
        }
    }
}

@Composable
fun MovieChip(text: String, prefixContent: (@Composable () -> Unit)? = null) {
    Button(
        onClick = {},
        shape = RoundedCornerShape(24.dp),
        elevation = ButtonDefaults.elevation(8.dp)
    ) {
        if (prefixContent != null)
            prefixContent()
        Text(text = text, Modifier.padding(horizontal = 5.dp))
    }
}