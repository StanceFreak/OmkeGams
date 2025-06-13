package com.stancefreak.pihakaseng.repository

import com.stancefreak.pihakaseng.model.remote.response.MoviesGenreList
import com.stancefreak.pihakaseng.model.remote.response.MoviesList
import com.stancefreak.pihakaseng.network.NetworkHelper
import retrofit2.Response
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val helper: NetworkHelper
) {

    suspend fun getMovieList(token: String): Response<MoviesList> {
        return helper.getMovieList(token)
    }

    suspend fun getMoviesGenre(token: String): Response<MoviesGenreList> {
        return helper.getMoviesGenre(token)
    }

}