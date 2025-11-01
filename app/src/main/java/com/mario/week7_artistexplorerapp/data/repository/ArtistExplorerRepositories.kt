package com.mario.week7_artistexplorerapp.data.repository

import com.mario.week7_artistexplorerapp.data.service.ArtistExplorerService
import com.mario.week7_artistexplorerapp.ui.model.AlbumModel
import com.mario.week7_artistexplorerapp.ui.model.ArtistModel
import com.mario.week7_artistexplorerapp.ui.model.TrackModel

class ArtistExplorerRepositories(private val service: ArtistExplorerService) {

    suspend fun getArtistName (artistName: String): ArtistModel {
        val responseArtists = service.getArtistNameFromAPI(artistName).body()!!
        val responseArtist = responseArtists.artists.firstOrNull()!! //intinya ngambil first artistnya

        return ArtistModel(
            nameArtist = responseArtist.strArtist,
//            idArtist = responseArtist.idArtist.toInt(),
            genreArtist = responseArtist.strGenre,
            imageURLArtist = responseArtist.strArtistThumb
        )
    }

    suspend fun getAlbum(artistName: String): List<AlbumModel> {
        val responseAlbums = service.getAlbumFromAPI(artistName).body()!!
        val responseAlbum = responseAlbums.album ?: emptyList()

        return responseAlbum
            //intinya filter ini buat ngambil yang sesuai dengan keinginan (yg ada nama album & ada gambarnya)
            .filter { it.strAlbum.isNotBlank() && it.strAlbumThumb.isNotBlank() }

            .map { //ini yg buat rapiin & sesuaiin datanya satu"
                AlbumModel(
                    nameAlbum = it.strAlbum,
                    idAlbum = it.idAlbum.toIntOrNull() ?: -1,
                    yearAlbum = it.intYearReleased ?: "",
                    genreAlbum = it.strGenre ?: "",
                    imageURLAlbum = it.strAlbumThumb ?: "",
                    descAlbum = it.strDescriptionEN ?: ""
//                    artistId = it.idArtist.toIntOrNull() ?: 0,
                )
            }
    }

    suspend fun getTrack(albumId: Int): List<TrackModel> {
        val responseTracks = service.getTrackFromAPI(albumId).body()!!
        val responseTrack = responseTracks.track ?: emptyList()

        return responseTrack
            .map { //ini yg buat rapiin & sesuaiin datanya satu", sama kek di atas
                TrackModel(
                    nameTrack = it.strTrack,
                    idTrack = it.idTrack.toInt(),
                    durationTrack = it.intDuration.toInt()
                )
        }
    }


}