package com.mario.week7_artistexplorerapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.mario.week7_artistexplorerapp.ui.route.AppView
import com.mario.week7_artistexplorerapp.ui.viewmodel.ArtistExplorerViewModel
import kotlin.text.isNotBlank


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
                ErrorView()//message = errorMessage)
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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(bottom = 24.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = artist.imageURLArtist),
                                contentDescription = "ArtistImage",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            Text(
                                text = artist.nameArtist.takeIf { it.isNotBlank() } ?: "Unknown Artist",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                            Text(
                                text = artist.genreArtist.takeIf { it.isNotBlank() } ?: "Unknown Genre",
                                fontSize = 16.sp,
                                color = Color.LightGray
                            )
                        }
                    }

                    items(albumList) { album ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            onClick = {
                                navController.navigate("${AppView.Album.name}/${album.idAlbum}")
                            }
                        ) {
                            Column(
                                modifier = Modifier.background(Color(0xFF282828)),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = album.imageURLAlbum),
                                    contentDescription = "AlbumCover",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                )
                                Text(
                                    text = album.nameAlbum.takeIf { it.isNotBlank() } ?: "Unknown Album",
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
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