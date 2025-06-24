package com.stancefreak.pihakaseng.network

import com.stancefreak.pihakaseng.model.remote.response.MovieDetail
import com.stancefreak.pihakaseng.model.remote.response.MovieTrailer
import com.stancefreak.pihakaseng.model.remote.response.MoviesGenreList
import com.stancefreak.pihakaseng.model.remote.response.MoviesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("movie/now_playing?region=ID")
    suspend fun getMovieList(
        @Header("Authorization") token: String,
    ): Response<MoviesList>

    @GET("genre/movie/list")
    suspend fun getMoviesGenre(
        @Header("Authorization") token: String,
    ): Response<MoviesGenreList>

    @GET("discover/movie?")
    suspend fun getMoviesByGenre(
        @Header("Authorization") token: String,
        @Query("with_genres") genreId: Int,
        @Query("sort_by") sortBy: String
    ): Response<MoviesList>

    @GET("movie/{id}?")
    suspend fun getMovieDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Query("append_to_response") query: String,
    ): Response<MovieDetail>

    @GET("movie/{id}/videos")
    suspend fun getMovieTrailer(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<MovieTrailer>

}