package com.mario.week7_artistexplorerapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mario.week7_artistexplorerapp.data.container.ArtistExplorerContainer
import com.mario.week7_artistexplorerapp.ui.model.AlbumModel
import com.mario.week7_artistexplorerapp.ui.model.ArtistModel
import com.mario.week7_artistexplorerapp.ui.model.TrackModel
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistExplorerViewModel : ViewModel() {

    // SET
    private val _artist = MutableStateFlow(ArtistModel())
    private val _albumList = MutableStateFlow<List<AlbumModel>>(emptyList())
    private val _trackList = MutableStateFlow<List<TrackModel>>(emptyList())
    private val _isError = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow("")

    // GET
    val artist: StateFlow<ArtistModel> = _artist
    val albumList: StateFlow<List<AlbumModel>> = _albumList
    val trackList: StateFlow<List<TrackModel>> = _trackList
    val isError: StateFlow<Boolean> = _isError
    val errorMessage: StateFlow<String> = _errorMessage

    // INIT
    //ngga ada initnya

    // FUNCTION
    private val repoPath = ArtistExplorerContainer().ArtistExplorerRepository

    fun loadArtist(artistName: String){
        viewModelScope.launch {
            _isError.value = false
            _errorMessage.value = ""
            try {
                val artistTemp = repoPath.getArtistName(artistName)
                _artist.value = artistTemp
                loadAlbumList(artistName)
            } catch (e: IOException) {
                _isError.value = true
                _errorMessage.value = "Tidak ada koneksi internet."
            } catch (e: Exception) {
                _isError.value = true
                _errorMessage.value = e.message ?: "Data tidak ditemukan."
            }
        }
    }

    fun loadAlbumList(artistName: String){
        viewModelScope.launch {
            try {
                val albumTemp = repoPath.getAlbum(artistName)
                _albumList.value = albumTemp
            } catch (e: IOException) {
                _albumList.value = emptyList()
            } catch (e: Exception) {
                _albumList.value = emptyList()
            }
        }
    }
}