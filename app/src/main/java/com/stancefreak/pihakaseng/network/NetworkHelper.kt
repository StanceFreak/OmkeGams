package com.stancefreak.pihakaseng.network

import com.stancefreak.pihakaseng.model.remote.response.MovieDetail
import com.stancefreak.pihakaseng.model.remote.response.MovieTrailer
import com.stancefreak.pihakaseng.model.remote.response.MoviesGenreList
import com.stancefreak.pihakaseng.model.remote.response.MoviesList
import retrofit2.Response

class NetworkHelper(
    private val service: NetworkService
): NetworkService {
    override suspend fun getMovieList(token: String): Response<MoviesList> {
        return service.getMovieList(token)
    }

    override suspend fun getMoviesGenre(token: String): Response<MoviesGenreList> {
        return service.getMoviesGenre(token)
    }

    override suspend fun getMoviesByGenre(
        token: String,
        genreId: Int,
        sortBy: String
    ): Response<MoviesList> {
        return service.getMoviesByGenre(token, genreId, sortBy)
    }

    override suspend fun getMovieDetail(
        token: String,
        id: Int,
        query: String
    ): Response<MovieDetail> {
        return service.getMovieDetail(token, id, query)
    }

    override suspend fun getMovieTrailer(token: String, id: Int): Response<MovieTrailer> {
        return service.getMovieTrailer(token, id)
    }
}