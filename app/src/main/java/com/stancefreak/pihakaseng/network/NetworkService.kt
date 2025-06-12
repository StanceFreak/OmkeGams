package com.stancefreak.pihakaseng.network

import com.stancefreak.combaja.data.response.MovieDetail
import com.stancefreak.combaja.data.response.MoviesGenreList
import com.stancefreak.combaja.data.response.MoviesPopularList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface NetworkService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Header("Authorization") token: String,
    ): Response<MoviesPopularList>

    @GET("genre/movie/list")
    suspend fun getMoviesGenre(
        @Header("Authorization") token: String,
    ): Response<MoviesGenreList>

    @GET("discover/movie?")
    suspend fun getMoviesByGenre(
        @Header("Authorization") token: String,
        @Query("with_genres") genreId: Int,
        @Query("sort_by") sortBy: String
    ): Response<MoviesPopularList>

    @GET("movie/{id}?")
    suspend fun getMovieDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Query("append_to_response") query: String,
    ): Response<MovieDetail>

}