package com.mario.week7_artistexplorerapp.data.container

import com.google.gson.GsonBuilder
import com.mario.week7_artistexplorerapp.data.repository.ArtistExplorerRepositories
import com.mario.week7_artistexplorerapp.data.service.ArtistExplorerService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtistExplorerContainer {

    companion object{
        val BASE_URL = "https://www.theaudiodb.com/api/v1/json/123/"
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: ArtistExplorerService by lazy {
        retrofit.create(ArtistExplorerService::class.java)
    }

    val ArtistExplorerRepository : ArtistExplorerRepositories by lazy {
        ArtistExplorerRepositories(retrofitService)
    }
}