package com.android.blinkitadminjc.screens
import com.android.blinkitadminjc.viewmodel.AuthViewModel
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.android.blinkitadminjc.R
import com.android.blinkitadminjc.navigation.Routes
import com.android.blinkitjc.utils.CommonDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneAuthScreen(viewModel: AuthViewModel, activity: ComponentActivity, navController: NavHostController) {
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var phoneNumber by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (uiState) {
            is AuthViewModel.AuthUiState.Idle -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.blinkit_logo),
                        contentDescription = "Blinkit Logo",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "India's last minute app",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Log in or sign up",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    TextField(
                        value = phoneNumber,
                        onValueChange = {
                            // Limit input to digits and max length of 10 for phone number
                            phoneNumber = it.filter { char -> char.isDigit() }.take(10)
                        },
                        label = { Text("Enter mobile number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        leadingIcon = { Text("+91") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = { viewModel.startPhoneNumberVerification(phoneNumber, activity = activity) },
                        enabled = phoneNumber.length == 10,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (phoneNumber.length == 10) Color.Yellow else Color(0xFF8B8B8B),
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Continue")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "By continuing, you agree to our Terms of service & Privacy policy",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            is AuthViewModel.AuthUiState.CodeSent -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.blinkit_logo),
                        contentDescription = "Blinkit Logo",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Enter the verification code",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    TextField(
                        value = code,
                        onValueChange = {

                            if (it.length <= 6) {
                                code = it.filter { char -> char.isDigit() }
                            }
                        },
                        label = { Text("Verification Code") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = { viewModel.verifyCode(code) },
                        enabled = code.length == 6,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (code.length == 6) Color.Yellow else Color(0xFF8B8B8B),
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Verify Code")
                    }
                }
            }
            is AuthViewModel.AuthUiState.Success -> {
                navController.navigate(Routes.BottomNav.routes)
            }
            is AuthViewModel.AuthUiState.Error -> {
                navController.navigate(Routes.BottomNav.routes)
                Toast.makeText(context, "Error: ${(uiState as AuthViewModel.AuthUiState.Error).message}", Toast.LENGTH_LONG).show()
            }

        }

        CommonDialog(isLoading = isLoading)
    }
}