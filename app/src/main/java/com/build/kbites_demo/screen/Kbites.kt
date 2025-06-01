package com.build.kbites_demo.screen

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.build.kbites_demo.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import android.util.Log

@Composable
fun KBitesScreen(navController: NavController) {
    val uriHandler = LocalUriHandler.current
    var email by remember { mutableStateOf(TextFieldValue("")) }
    val wellsFargoRed = Color(0xFFB31B1B)
    val wellsFargoWhite = Color(0xFFFFFFFF)
    val backgroundGrey = Color(0xFFF5F5F5)
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    var authFailed by remember { mutableStateOf(false) }

    // GoogleSignIn setup
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        isLoading = false
        val data = result.data
        var success = false

        if(!authFailed) {
            navController.navigate("baking"){
                popUpTo("kBites") { inclusive = true }
            }
        }

        if (data != null) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken
                if (!idToken.isNullOrEmpty()) {
                    navController.navigate("baking") {
                        popUpTo("kBites") { inclusive = true }
                    }
                    success = true
                }
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
        if (!success) {
            authFailed = false
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundGrey
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Wells Fargo logo or styled text at the top
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "WELLS FARGO",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = wellsFargoRed,
                    letterSpacing = 2.sp,
                )
            }
            // Main content in the center
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "KBites",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "The gen  alpha Knowledge Base",
                    fontSize = 14.sp,
                    color = Color.Red,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = wellsFargoRed) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = wellsFargoRed,
                        unfocusedTextColor = wellsFargoRed,
                        focusedContainerColor = wellsFargoWhite,
                        unfocusedContainerColor = wellsFargoWhite,
                        focusedBorderColor = wellsFargoRed,
                        unfocusedBorderColor = wellsFargoRed,
                        cursorColor = wellsFargoRed
                    )
                )

                Spacer(modifier = Modifier.height(100.dp))
                Button(
                    onClick = {
                        isLoading = true // <-- Show progress bar
                        // Launch Google Sign-In intent
                        val signInIntent = googleSignInClient.signInIntent
                        googleSignInLauncher.launch(signInIntent)
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(40.dp)
                        .padding(horizontal = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = wellsFargoRed
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = "Google logo",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Continue with Google", color = wellsFargoRed)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        isLoading = true
                        // Open Apple sign-in page in browser (demo only)
                        uriHandler.openUri("https://appleid.apple.com/auth/authorize?client_id=YOUR_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI&response_type=code%20id_token&scope=name%20email")
                        isLoading = false
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(40.dp)
                        .padding(horizontal = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = wellsFargoRed
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.apple_ico),
                            contentDescription = "Apple logo",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Continue with Apple", color = wellsFargoRed)
                    }
                }
            }
            // Privacy Policy at the bottom center
            Text(
                text = "Privacy Policy",
                color = wellsFargoRed,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clickable { uriHandler.openUri("https://google.com") }
                    .padding(8.dp)
            )
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .background(Color(0x88000000)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = wellsFargoRed)
                }
            }
            if (authFailed) {
                AlertDialog(
                    onDismissRequest = { authFailed = false },
                    title = { Text("Authentication Failed") },
                    text = { Text("Google authentication failed. Please try again.") },
                    confirmButton = {
                        Button(onClick = { authFailed = false }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KBitesScreenPreview() {
    val navController = androidx.navigation.compose.rememberNavController()
        KBitesScreen(navController)
}

