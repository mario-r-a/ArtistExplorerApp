package com.mario.week7_artistexplorerapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.mario.week7_artistexplorerapp.ui.route.AppView
import com.mario.week7_artistexplorerapp.ui.viewmodel.ArtistExplorerViewModel

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    viewModel: ArtistExplorerViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {
    val artist by viewModel.artist.collectAsState()
    val albumList by viewModel.albumList.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        if (artist.nameArtist.isBlank() && !isError) {
            viewModel.loadArtist("ed sheeran")
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF282828))
    ) {
        when {
            isError -> {
                ErrorView()
            }
            artist.nameArtist.isBlank() -> {
                LoadingView()
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = artist.imageURLArtist),
                                contentDescription = "Artist Background",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .border(
                                        width = 1.dp,
                                        color = Color.White.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = artist.nameArtist,
                                    fontSize = 28.sp,
                                    color = Color(0xFFdcdcdc)
                                )
                                Text(
                                    text = artist.genreArtist,
                                    fontSize = 14.sp,
                                    color = Color(0xFFdcdcdc)
                                )
                            }
                        }
                    }

                    item(span = {GridItemSpan(maxLineSpan)}){
                        Text(
                            text = "Albums",
                            fontSize = 16.sp,
                            color = Color(0xFFdcdcdc),
                            modifier = Modifier.padding(vertical = 2.dp, horizontal = 6.dp)
                        )
                    }

                    items(albumList) { album ->
                        AlbumCard(
                            album = album,
                            onAlbumClick = { albumId ->
                                navController.navigate("${AppView.Album.name}/$albumId")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeViewPreview() {
    HomeView()
}