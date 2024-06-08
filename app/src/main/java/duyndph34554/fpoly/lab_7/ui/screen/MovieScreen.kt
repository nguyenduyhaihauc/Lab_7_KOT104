package duyndph34554.fpoly.lab_7.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import duyndph34554.fpoly.lab_7.model.Movie
import duyndph34554.fpoly.lab_7.model.MovieViewModel

enum class ListType {
    ROW, COLUMN, GRID
}

@Composable
fun Movie(value: List<Movie>, navController: NavHostController) {
    val mainViewModel: MovieViewModel = viewModel()
    val movieState = mainViewModel.movies.observeAsState(initial = emptyList())
//    MovieScreen(movie = movieState.value)
}

@Composable
fun MovieScreen(movie: List<Movie>) {
    var listType by remember { mutableStateOf(ListType.ROW) }

    Column {
        Row (
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { listType = ListType.ROW }) {
                Text(text = "Row")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { listType = ListType.COLUMN }) {
                Text(text = "Column")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { listType = ListType.GRID }) {
                Text(text = "Gird")
            }
        }

        when(listType) {
            ListType.ROW -> MovieRow(movie = movie)
            ListType.COLUMN -> MovieColumn(movie = movie)
            ListType.GRID -> MovieGird(movie = movie)
        }
    }
}

//MovieRow
@Composable
fun MovieRow(movie: List<Movie>) {
    LazyRow (
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movie.size) { index ->
            MovieItem(movie = movie[index], listType = ListType.ROW)
        }
    }
}

//MovieColumn
@Composable
fun MovieColumn(movie: List<Movie>) {
    LazyColumn (
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movie.size) { index ->
            MovieItemColumn(movie = movie[index], listType = ListType.COLUMN)
        }
    }
}

//Movie Gird
@Composable
fun MovieGird(movie: List<Movie>) {
    val girdState = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        state = girdState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp,
        contentPadding = PaddingValues(8.dp)
    ) {
        items(movie.size) { index ->
            MovieItem(movie = movie[index], listType = ListType.GRID)
        }
    }
}


// MovieItemRow
@Composable
fun MovieItem(movie: Movie, listType: ListType) {
    Card (
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column (
            modifier = Modifier.then(getItemSizeModifier(listType = listType))
        ) {
            AsyncImage(model = movie.posterUrl,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

            Column (
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = movie.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                BoldValueText(label = "Thời lượng", value = movie.duration)

                BoldValueText(label = "Khởi chiếu", value = movie.releaseData)

            }
        }
    }
}


//ItemColumn

@Composable
fun MovieItemColumn(movie: Movie, listType: ListType) {
    Card (
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(model = movie.posterUrl,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .then(getItemSizeModifier(listType = listType)
                        .wrapContentHeight())
            )

            Column (
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = movie.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                BoldValueText(label = "Thời lượng",
                    value = movie.duration)
                BoldValueText(label = "Khởi chiếu",
                    value = movie.releaseData)
                BoldValueText(label = "Thể loại",
                    value = movie.genre)

                Text(text = "Tóm tắt nội dung",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                )

                Text(text = movie.shortDescription,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = 2.dp)
                )
            }
        }
    }
}

//Cac ham ho tro khac
//ham de set giao dien listType
@Composable
private fun getItemSizeModifier(listType: ListType) : Modifier {
    return when(listType) {
        ListType.ROW -> Modifier.width(175.dp)
        ListType.COLUMN -> Modifier.width(130.dp)
        ListType.GRID -> Modifier.fillMaxWidth()
    }
}

//Ham cau hinh Text
@Composable
fun BoldValueText(label: String, value: String, style: TextStyle = MaterialTheme.typography.bodySmall) {
    Text(buildAnnotatedString {
        append(label)
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(value)
        }
    }, style = style)
}