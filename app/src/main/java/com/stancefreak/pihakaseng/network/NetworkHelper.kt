package com.stancefreak.pihakaseng.network

import com.stancefreak.combaja.data.response.MovieDetail
import com.stancefreak.combaja.data.response.MoviesGenreList
import com.stancefreak.combaja.data.response.MoviesPopularList
import retrofit2.Response

class NetworkHelper(
    private val service: NetworkService
): NetworkService {
    override suspend fun getPopularMovies(token: String): Response<MoviesPopularList> {
        return service.getPopularMovies(token)
    }

    override suspend fun getMoviesGenre(token: String): Response<MoviesGenreList> {
        return service.getMoviesGenre(token)
    }

    override suspend fun getMoviesByGenre(
        token: String,
        genreId: Int,
        sortBy: String
    ): Response<MoviesPopularList> {
        return service.getMoviesByGenre(token, genreId, sortBy)
    }

    override suspend fun getMovieDetail(
        token: String,
        id: Int,
        query: String
    ): Response<MovieDetail> {
        return service.getMovieDetail(token, id, query)
    }
}