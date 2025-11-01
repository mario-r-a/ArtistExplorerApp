package com.mario.week7_artistexplorerapp.ui.route

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mario.week7_artistexplorerapp.ui.view.ErrorView
import com.mario.week7_artistexplorerapp.ui.view.HomeView
import com.mario.week7_artistexplorerapp.ui.view.LoadingView
import com.mario.week7_artistexplorerapp.ui.viewmodel.ArtistExplorerViewModel
import kotlin.io.path.name
import kotlin.text.isNotBlank

enum class AppView(val title: String, val icon: ImageVector? = null){
    Home("Artist Explorer"),
    Album("Album Detail"),
    Loading("Loading..."),
    Error("Error")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppRoute(){

    val viewModel: ArtistExplorerViewModel = viewModel()
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val currentView = AppView.entries.find {it.name == currentRoute}

    val currentArtist by viewModel.artist.collectAsState()
//    val selectedAlbum by viewModel.selectedAlbum.collectAsState()

    val topBarTitle = when (currentView) {
        AppView.Home -> currentArtist.nameArtist.takeIf { it?.isNotBlank() == true } ?: currentView.title
//        AppView.Album -> selectedAlbum.nameAlbum
        AppView.Loading -> "Loading..."
        AppView.Error -> "Error"
        else -> ""
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                currentView = currentView,
                currentTitle = topBarTitle,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ){ innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = AppView.Home.name
        ){
            composable(route = AppView.Home.name){
                HomeView(viewModel = viewModel)
            }

            composable(route = "${AppView.Album.name}/{albumId}"){ backStackEntry ->
                val albumId = backStackEntry.arguments?.getString("albumId")
                if (albumId != null) {
//                    AlbumView(viewModel = viewModel)
                }
            }

            composable(route = AppView.Loading.name){
                LoadingView()
            }

            composable(route = AppView.Error.name){
                ErrorView()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    currentView: AppView?,
    currentTitle: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    CenterAlignedTopAppBar(
        title = {
            Text(text = currentTitle)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1c2021),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
        ),

        modifier = modifier,
        navigationIcon = {
            if(canNavigateBack && currentView == AppView.Album){
                IconButton(onClick = navigateUp){
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
    )
}