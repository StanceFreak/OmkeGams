package com.stancefreak.pihakaseng.repository

import com.stancefreak.pihakaseng.model.remote.response.MovieDetail
import com.stancefreak.pihakaseng.model.remote.response.MoviesGenreList
import com.stancefreak.pihakaseng.model.remote.response.MoviesList
import retrofit2.Response
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val remote: RemoteRepository
) {

    suspend fun getMovieList(token: String): Response<MoviesList> {
        return remote.getMovieList(token)
    }

    suspend fun getMoviesGenre(token: String): Response<MoviesGenreList> {
        return remote.getMoviesGenre(token)
    }

    suspend fun getMovieDetail(
        token: String,
        id: Int,
        query: String
    ): Response<MovieDetail> {
        return remote.getMovieDetail(token, id, query)
    }

}