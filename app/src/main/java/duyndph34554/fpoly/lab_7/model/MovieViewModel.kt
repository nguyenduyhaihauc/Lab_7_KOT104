package duyndph34554.fpoly.lab_7.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import duyndph34554.fpoly.lab_7.data.RetrofitService
import duyndph34554.fpoly.lab_7.data.toMovie
import kotlinx.coroutines.launch

class MovieViewModel: ViewModel() {
    private val _movie = MutableLiveData<List<Movie>>()

    val movies: LiveData<List<Movie>> = _movie

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            try {
                val response = RetrofitService().movieService.getListFilm()
                if (response.isSuccessful) {
                    _movie.postValue(response.body()?.map { it.toMovie() })
                } else {
                    _movie.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "getMovies: " + e.message)
                _movie.postValue(emptyList())
            }
        }
    }
}