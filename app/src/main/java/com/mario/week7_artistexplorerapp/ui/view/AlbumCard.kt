package com.mario.week7_artistexplorerapp.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.mario.week7_artistexplorerapp.ui.model.AlbumModel

@Composable
fun AlbumCard(
    album: AlbumModel,
    onAlbumClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        onClick = { onAlbumClick(album.idAlbum) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color(0xFF1c2021)
        ),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
    ) {
        Column() {
            Image(
                painter = rememberAsyncImagePainter(model = album.imageURLAlbum),
                contentDescription = "Album Cover: ${album.nameAlbum}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            )
            Text(
                text = album.nameAlbum,
                fontSize = 14.sp,
                color = Color(0xFFdcdcdc),
                maxLines = 1,
                modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp)
            )
            Text(
                text = "${album.yearAlbum} • ${album.genreAlbum}",
                fontSize = 12.sp,
                color = Color(0xFFdcdcdc),
                modifier = Modifier.padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AlbumCardPreview(){
    val dummyAlbum = AlbumModel(
        idAlbum = 2115888,
        nameAlbum = "÷ (Divide)",
        yearAlbum = "2017",
        imageURLAlbum = "https://www.theaudiodb.com/images/media/album/thumb/xxyvup1483637699.jpg"
    )

    AlbumCard(
        album = dummyAlbum,
        onAlbumClick = {
        },
        modifier = Modifier.padding(8.dp)
    )
}
