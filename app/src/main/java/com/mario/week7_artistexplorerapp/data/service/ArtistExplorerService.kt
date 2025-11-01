package com.mario.week7_artistexplorerapp.data.service

import com.mario.week7_artistexplorerapp.data.dto.ResponseAlbum
import com.mario.week7_artistexplorerapp.data.dto.ResponseArtist
import com.mario.week7_artistexplorerapp.data.dto.ResponseTrack
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistExplorerService {

    @GET("search.php")
    suspend fun getArtistNameFromAPI(
        @Query("s") artistName: String,
    ): Response<ResponseArtist>

    @GET("searchalbum.php")
    suspend fun getAlbumFromAPI(
        @Query("s") artistName: String,
    ): Response<ResponseAlbum>

    @GET("album.php")
    suspend fun getAlbumDetailFromAPI(
        @Query("m") albumId: Int,
    ): Response<ResponseAlbum>

    @GET("track.php")
    suspend fun getTrackFromAPI(
        @Query("m") albumId: Int,
    ): Response<ResponseTrack>

}