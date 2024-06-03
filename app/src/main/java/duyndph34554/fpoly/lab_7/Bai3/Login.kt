package duyndph34554.fpoly.lab_7.Bai3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController
import duyndph34554.fpoly.lab_7.Bai2.Screen
import duyndph34554.fpoly.lab_7.R


class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

//Man tong
@Composable
fun LoginScreen(navController: NavController) {
    val loginScreenModel: LoginScreenModel = viewModel()
    LoginCard(navController = navController, loginScreenModel = loginScreenModel)
}

@Composable
fun LoginCard(navController: NavController, loginScreenModel: LoginScreenModel){
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    HandleLoginState(snackbarHostState = snackbarHostState, loginScreenModel = loginScreenModel, navController = navController)

    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        paddingValues ->
        LoginForm(loginScreenModel = loginScreenModel, paddingValues = paddingValues)
    }
}

@Composable
fun HandleLoginState(
    snackbarHostState: SnackbarHostState,
    loginScreenModel: LoginScreenModel,
    navController: NavController
) {
    val isAuthenticated by loginScreenModel.isAuthentication.observeAsState()

    LaunchedEffect(key1 = isAuthenticated) {
        when(isAuthenticated) {
            true -> {
                navController.navigate(Screen.MOVIE_SCREEN.route) {
                    popUpTo(Screen.LOGIN.route) { inclusive = true }
                }
            }

            false -> {
                snackbarHostState.showSnackbar(
                    message = "Invalid username or password",
                    duration = SnackbarDuration.Short
                )
                loginScreenModel.resetAuthenticationState()
            }

            null -> {}
        }
    }
}

//loginForm
@Composable
fun LoginForm(
    loginScreenModel: LoginScreenModel,
    paddingValues: PaddingValues
) {
    val userNameState by loginScreenModel.username.observeAsState("")
    val rememberMeState by loginScreenModel.rememberMe.observeAsState(false)
    var username by remember { mutableStateOf(userNameState) }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember {
        mutableStateOf(rememberMeState)
    }
    val isLoginEnabled = username.isNotBlank() && password.isNotBlank()
    
    LaunchedEffect(key1 = userNameState, key2 = rememberMeState) {
        username = userNameState
        rememberMe = rememberMeState
        Log.d("PAM", "LoginForm: username $userNameState rememberMeState $rememberMeState")
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(paddingValues),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp, 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo")

                Spacer(modifier = Modifier.height(20.dp))
                UsernameField(username = username, onUsernameChange = {username = it})
                PasswordField(password = password, onPasswordChange = {password = it})
                RememberMeSwitch(rememberMe = rememberMe) {
                    isChecked -> rememberMe = isChecked
                }
                Spacer(modifier = Modifier.height(16.dp))

                LoginButton(isEnabled = isLoginEnabled) {
                    loginScreenModel.Login(username, password, rememberMe)
                }
            }
        }
    }
}

@Composable
fun UsernameField(username: String, onUsernameChange: (String) -> Unit) {
    OutlinedTextField(value = username,
        onValueChange = onUsernameChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Username")}
    )
}

@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Password")},
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun LoginButton(isEnabled: Boolean, onLoginClick: () -> Unit) {
    Button(
        onClick = onLoginClick,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled) Color.DarkGray else Color.LightGray,
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(40.dp)
    ) {
        Text(text = "Login", fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RememberMeSwitch(rememberMe: Boolean, onCheckedChange: (Boolean) -> Unit){
    var isCheched by remember {
        mutableStateOf(rememberMe)
    }

    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(checked = isCheched,
            onCheckedChange = {
                isCheched = it
                onCheckedChange(it)
            },
            modifier = Modifier
                .scale(0.75f)
                .padding(0.dp)
        )
        Text(text = "Remember Me", modifier = Modifier.padding(start = 12.dp))
    }
}