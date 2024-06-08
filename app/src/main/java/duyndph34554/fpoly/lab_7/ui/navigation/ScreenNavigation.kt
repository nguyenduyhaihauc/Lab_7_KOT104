package duyndph34554.fpoly.lab_7.ui.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import duyndph34554.fpoly.lab_7.ui.screen.LoginScreen
import duyndph34554.fpoly.lab_7.model.MovieViewModel
import duyndph34554.fpoly.lab_7.ui.screen.MovieScreen

//Quan ly cac man hinh
enum class Screen(val route: String) {
    LOGIN("Login"),
    MOVIE_SCREEN("MovieScreen"),
    SCREEN1("Screen1"),
    SCREEN2("Screen2"),
    SCREEN3("Screen3")
}

//class Navigation : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            ScreenNavigation()
//        }
//    }
//}
//Cau hinh va quan ly cac Screen
@Composable
fun GetLayoutScreenNavigation() {
    val navController = rememberNavController()

    val mainViewModel: MovieViewModel = viewModel()
    val moviesState = mainViewModel.movies.observeAsState(initial = emptyList())

    NavHost(navController = navController,
        startDestination = Screen.LOGIN.route
    ) {
        composable(Screen.LOGIN.route) { LoginScreen(navController = navController) }
        composable(Screen.MOVIE_SCREEN.route) { MovieScreen(movie = moviesState.value)}
        composable(Screen.SCREEN1.route) { Screen1(navController = navController) }
        composable(Screen.SCREEN2.route) { Screen2(navController = navController) }
        composable(Screen.SCREEN3.route) { Screen3(navController = navController) }
    }
}

//Screen1
@Composable
fun Screen1(navController: NavController) {
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red)
            .clickable { navController.navigate(Screen.SCREEN2.route) },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Screen 1", color = Color.White, fontSize = 20.sp)
    }
}



//Screen 2
@Composable
fun Screen2(navController: NavController) {
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Yellow)
            .clickable { navController.navigate(Screen.SCREEN3.route) },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Screen 2", color = Color.White, fontSize = 20.sp)
    }
}

//Screen 3
@Composable
fun Screen3(navController: NavController) {
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green)
            .clickable { navController.navigate(Screen.SCREEN1.route) },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Screen 3", color = Color.White, fontSize = 20.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun showScreen() {

}