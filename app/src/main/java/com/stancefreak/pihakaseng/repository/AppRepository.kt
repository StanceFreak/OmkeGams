package com.stancefreak.pihakaseng.repository

import com.stancefreak.pihakaseng.model.remote.response.MovieDetail
import com.stancefreak.pihakaseng.model.remote.response.MoviesGenreList
import com.stancefreak.pihakaseng.model.remote.response.MoviesList
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val remote: RemoteRepository,
    private val local: LocalRepository
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

    suspend fun storeVpState(pref: Int) {
        return local.storeVpState(pref)
    }

    fun fetchVpState(): Flow<Int> {
        return local.fetchVpState()
    }

    suspend fun removeVpState() {
        return local.removeVpState()
    }

}