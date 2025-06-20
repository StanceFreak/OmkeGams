package com.stancefreak.pihakaseng.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stancefreak.pihakaseng.BuildConfig
import com.stancefreak.pihakaseng.model.remote.response.EventState
import com.stancefreak.pihakaseng.model.remote.response.MovieDetail
import com.stancefreak.pihakaseng.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repo: AppRepository
):ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>()
    fun observeLoadingState(): LiveData<Boolean> = _loadingState

    private val _eventState = MutableLiveData<ArrayList<EventState>?>()
    fun observeEventState(): LiveData<ArrayList<EventState>?> = _eventState

    private val _movieDetail = MutableLiveData<MovieDetail?>()
    fun observerMovieDetail(): LiveData<MovieDetail?> = _movieDetail

    private val eventList = ArrayList<EventState>()

    fun fetchMovieDetail(
        id: Int
    ) {
        _loadingState.postValue(true)
        _eventState.postValue(null)
        viewModelScope.launch {
            try {
                val detailResponse = repo.getMovieDetail("Bearer ${BuildConfig.ACCESS_TOKEN}", id, "release_dates,credits")
                if (detailResponse.isSuccessful) {
                    _loadingState.postValue(false)
                    eventList.apply {
                        clear()
                        add(EventState("success", "success getting data from API"))
                    }
                    _movieDetail.postValue(detailResponse.body())
                    _eventState.postValue(eventList)
                }
                else {
                    val genreErr = detailResponse.errorBody()?.string()?.let { JSONObject(it) }
                    _loadingState.postValue(false)
                    eventList.apply {
                        clear()
                        genreErr?.getString("message")?.let { msg ->
                            add(EventState("srvErr", msg))
                        }
                    }
                    _eventState.postValue(eventList)
                }
            }
            catch (e: Exception) {
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