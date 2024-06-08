package duyndph34554.fpoly.lab_7.data

import duyndph34554.fpoly.lab_7.data.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {
    @GET
    suspend fun getListFilm(): Response<List<MovieResponse>>
}