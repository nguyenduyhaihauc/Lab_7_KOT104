package duyndph34554.fpoly.lab_7.data

import duyndph34554.fpoly.lab_7.model.Movie

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = this.filmId,
        title = this.filmName,
        duration = this.duration,
        releaseData = this.releaseDate,
        genre = this.genre,
        shortDescription = this.description,
        posterUrl = this.image
    )
}