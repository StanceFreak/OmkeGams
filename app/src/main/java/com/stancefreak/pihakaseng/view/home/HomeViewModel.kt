package com.stancefreak.pihakaseng.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stancefreak.pihakaseng.BuildConfig
import com.stancefreak.pihakaseng.model.remote.response.EventState
import com.stancefreak.pihakaseng.model.remote.response.MoviesGenreList
import com.stancefreak.pihakaseng.model.remote.response.MoviesList
import com.stancefreak.pihakaseng.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: AppRepository
):ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>()
    fun observeLoadingState(): LiveData<Boolean> = _loadingState

    private val _eventState = MutableLiveData<ArrayList<EventState>?>()
    fun observeEventState(): LiveData<ArrayList<EventState>?> = _eventState

    private val _movieList = MutableLiveData<MoviesList>()
    fun observeMovieList(): LiveData<MoviesList> = _movieList

    private val _genreList = MutableLiveData<MoviesGenreList>()
    fun observeGenreList(): LiveData<MoviesGenreList> = _genreList

    private val eventList = ArrayList<EventState>()

    fun fetchHomeContent() {
        _loadingState.postValue(true)
        _eventState.postValue(null)
        viewModelScope.launch {
            try {
                val movieResponse = repo.getMovieList("Bearer ${BuildConfig.ACCESS_TOKEN}")
                val genreResponse = repo.getMoviesGenre("Bearer ${BuildConfig.ACCESS_TOKEN}")
                if (movieResponse.isSuccessful && genreResponse.isSuccessful) {
                    _loadingState.postValue(false)
                    eventList.apply {
                        clear()
                        add(EventState("success", "success getting data from API"))
                    }
                    _genreList.postValue(genreResponse.body())
                    _movieList.postValue(movieResponse.body())
                    _eventState.postValue(eventList)
                }
                else {
                    val movieErr = movieResponse.errorBody()?.string()?.let { JSONObject(it) }
                    val genreErr = genreResponse.errorBody()?.string()?.let { JSONObject(it) }
                    _loadingState.postValue(false)
                    eventList.apply {
                        clear()
                        movieErr?.getString("message")?.let { msg ->
                            add(EventState("srvErr", msg))
                        }
                        genreErr?.getString("message")?.let { msg ->
                            add(EventState("srvErr", msg))
                        }
                    }
                    _eventState.postValue(eventList)
                }
            }
            catch (e:Exception) {
                _loadingState.postValue(false)
                eventList.apply {
                    clear()
                    EventState("netErr", e.message)
                }
                _eventState.postValue(eventList)
                e.printStackTrace()
            }
        }
    }

}