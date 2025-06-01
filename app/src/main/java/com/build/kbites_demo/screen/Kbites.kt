package com.build.kbites_demo.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import com.build.kbites_demo.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

val images1 = arrayOf(
    // Image generated using Gemini from the prompt "cupcake image"
   com.build.kbites_demo.R.drawable.baked_goods_1
)

@Composable
fun KBitesScreen() {
    val uriHandler = LocalUriHandler.current
    var email by remember { mutableStateOf(TextFieldValue("")) }
    val wellsFargoRed = Color(0xFFB31B1B)
    val wellsFargoYellow = Color(0xFFFFCC00)
    val wellsFargoWhite = Color(0xFFFFFFFF)
    val backgroundGrey = Color(0xFFF5F5F5)
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

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
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            // TODO: Handle successful sign-in (account contains user info)
        } catch (e: ApiException) {
            // TODO: Handle sign-in failure
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
                val bitmap = BitmapFactory.decodeResource(
                    context.resources,
                    com.build.kbites_demo.R.drawable.google

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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KBitesScreenPreview() {
    KBitesScreen()
}

