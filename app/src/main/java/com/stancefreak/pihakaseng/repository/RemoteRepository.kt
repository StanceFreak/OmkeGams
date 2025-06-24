package com.stancefreak.pihakaseng.repository

import com.stancefreak.pihakaseng.model.remote.response.MovieDetail
import com.stancefreak.pihakaseng.model.remote.response.MovieTrailer
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

    suspend fun getMovieDetail(
        token: String,
        id: Int,
        query: String
    ): Response<MovieDetail> {
        return helper.getMovieDetail(token, id, query)
    }

    suspend fun getMovieTrailer(token: String, id: Int): Response<MovieTrailer> {
        return helper.getMovieTrailer(token, id)
    }

}