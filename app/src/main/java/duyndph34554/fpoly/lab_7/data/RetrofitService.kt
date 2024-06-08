package duyndph34554.fpoly.lab_7.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class RetrofitService() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("<BASE_URL>")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val movieService: MovieService by lazy {
        retrofit.create(MovieService::class.java)
    }
}