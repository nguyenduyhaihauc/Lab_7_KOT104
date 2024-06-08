package duyndph34554.fpoly.lab_7.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginScreenModel: ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _rememberMe = MutableLiveData<Boolean>()
    val rememberMe: LiveData<Boolean> = _rememberMe

    private val _isAuthentication = MutableLiveData<Boolean?>()
    val isAuthentication: LiveData<Boolean?> = _isAuthentication

    fun Login(username: String, password: String, rememberMe: Boolean) {
        if (username.equals("admin")  && password.equals("123")){
            _isAuthentication.value = true
        } else {
            _isAuthentication.value = false
        }
    }

    fun resetAuthenticationState() {
        _isAuthentication.value = null
    }
}