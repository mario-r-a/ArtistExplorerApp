package com.mario.week7_artistexplorerapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mario.week7_artistexplorerapp.ui.viewmodel.ArtistExplorerViewModel

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    viewModel: ArtistExplorerViewModel = viewModel()
){
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF282828)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Error: ${errorMessage}",
            fontSize = 16.sp,
            color = Color(0xFF912020),
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ErrorPreview(){
    ErrorView()
}