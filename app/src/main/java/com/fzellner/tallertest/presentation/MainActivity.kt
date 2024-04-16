package com.fzellner.tallertest.presentation

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fzellner.tallertest.R
import com.fzellner.tallertest.presentation.state.LoginViewState
import com.fzellner.tallertest.presentation.ui.theme.TallerTestTheme
import com.fzellner.tallertest.presentation.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TallerTestTheme {
                val viewModel: LoginViewModel = hiltViewModel()
                val context = LocalContext.current
                val viewState = viewModel.loginViewState.collectAsState().value
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.padding(it)) {
                        LoginForm { userEmail, password ->
                            viewModel.doLogin(userEmail, password)
                        }
                        when (viewState) {
                            LoginViewState.Error -> showToastMessage(context, stringResource(id = R.string.error))
                            LoginViewState.Success -> showToastMessage(context, stringResource(id = R.string.success))
                            is LoginViewState.Loading -> AnimatedVisibility(visible = viewState.isLoading) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(120.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun showToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


@Composable
fun LoginForm(onClick: (userEmail: String, password: String) -> Unit) {

    val userEmail = remember {
        mutableStateOf("")
    }
    val userPassword = remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = userEmail.value,
                onValueChange = {
                    userEmail.value = it
                },
                label = {
                    Text(text = stringResource(id = R.string.email))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = userPassword.value,
                onValueChange = {
                    userPassword.value = it
                },
                label = {
                    Text(text = stringResource(id = R.string.password))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword
                )
            )
            Button(onClick = {
                onClick(userEmail.value, userPassword.value)
            }) {
                Text(text = stringResource(id = R.string.login))
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TallerTestTheme {
        LoginForm(
            onClick = { userEmail, password -> }
        )
    }
}