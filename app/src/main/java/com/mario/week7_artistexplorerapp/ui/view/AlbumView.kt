package com.mario.week7_artistexplorerapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.mario.week7_artistexplorerapp.ui.model.AlbumModel
import com.mario.week7_artistexplorerapp.ui.model.TrackModel
import com.mario.week7_artistexplorerapp.ui.viewmodel.ArtistExplorerViewModel
import java.util.concurrent.TimeUnit

@Composable
fun AlbumView(
    albumId: String,
    viewModel: ArtistExplorerViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val selectedAlbum by viewModel.selectedAlbum.collectAsState()
    val trackList by viewModel.trackList.collectAsState()
    val isError by viewModel.isError.collectAsState()

    LaunchedEffect(albumId) {
        albumId.toIntOrNull()?.let { id ->
            viewModel.selectAlbumById(id)
        }
    }

    when {
        isError -> ErrorView()
        trackList.isEmpty() && selectedAlbum.nameAlbum.isNotBlank() -> LoadingView()
        else -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color(0xFF282828)),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .background(Color(0xFF1c2021), shape = RoundedCornerShape(12.dp))
                            .border(
                                width = 1.dp,
                                color = Color.White.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = selectedAlbum.imageURLAlbum),
                            contentDescription = "Cover for ${selectedAlbum.nameAlbum}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = selectedAlbum.nameAlbum,
                            fontSize = 20.sp,
                            color = Color(0xFFdcdcdc),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Text(
                            text = "${selectedAlbum.yearAlbum} • ${selectedAlbum.genreAlbum}",
                            fontSize = 14.sp,
                            color = Color.LightGray,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        if (selectedAlbum.descAlbum.isNotBlank()) {
                            Text(
                                text = viewModel.potongKata(selectedAlbum.descAlbum),
                                fontSize = 14.sp,
                                color = Color.LightGray,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }

                item {
                    Text(
                        text = "Tracks",
                        color = Color(0xFFccb365),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 8.dp),
                        textAlign = TextAlign.Start
                    )
                }


                items(trackList) { track ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = track.nameTrack,
                                color = Color(0xFFdcdcdc),
                                fontSize = 15.sp,
                                maxLines = 1
                            )
                        }

                        Text(
                            text = viewModel.formatDuration(track.durationTrack.toLong()),
                            fontWeight = FontWeight.Light,
                            color = Color(0xFFdcdcdc),
                            fontSize = 12.sp
                        )
                    }
                    HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AlbumViewContentPreview() {
    // Data dummy untuk album dan lagu
    val dummyAlbum = AlbumModel(
        nameAlbum = "÷ (Divide)",
        yearAlbum = "2017",
        genreAlbum = "Pop",
        descAlbum = "Divide is the third studio album by English singer-songwriter Ed Sheeran. It was released on 3 March 2017 through Asylum Records and Atlantic Records.",
        imageURLAlbum = ""
    )
    val dummyTrackList = listOf(
        TrackModel(nameTrack = "Shape of You", durationTrack = 233713),
        TrackModel(nameTrack = "Castle on the Hill", durationTrack = 261154),
        TrackModel(nameTrack = "Perfect", durationTrack = 263400),
        TrackModel(nameTrack = "Galway Girl", durationTrack = 170827)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF282828)),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = dummyAlbum.nameAlbum,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xFFdcdcdc),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Album by Ed Sheeran • ${dummyAlbum.yearAlbum}",
                    fontSize = 14.sp,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (dummyAlbum.descAlbum.isNotBlank()) {
                    Text(
                        text = dummyAlbum.descAlbum,
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        items(dummyTrackList) { track ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = track.nameTrack,
                        color = Color(0xFFdcdcdc),
                        fontSize = 16.sp,
                        maxLines = 1
                    )
                }
                val minutes = TimeUnit.MILLISECONDS.toMinutes(track.durationTrack.toLong())
                val seconds = TimeUnit.MILLISECONDS.toSeconds(track.durationTrack.toLong()) -
                        TimeUnit.MINUTES.toSeconds(minutes)
                val durationString = String.format("%02d:%02d", minutes, seconds)

                Text(
                    text = durationString,
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }
            HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
        }
    }
}