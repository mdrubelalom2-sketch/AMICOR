
package com.amicor.dating

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AuthPage()
                }
            }
        }
    }
}

@Composable
fun AuthPage() {
    val auth = remember { FirebaseAuth.getInstance() }

    var isLogin by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "AMICOR",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = if (isLogin) "Login" else "Create Account",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!isLogin) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Your Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password (minimum 6 characters)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank() ||
                    (!isLogin && name.isBlank())
                ) {
                    message = "Please fill in all fields."
                } else if (password.length < 6) {
                    message = "Password must be at least 6 characters."
                } else {
                    loading = true
                    message = ""

                    if (isLogin) {
                        auth.signInWithEmailAndPassword(
                            email.trim(),
                            password
                        ).addOnCompleteListener { task ->
                            loading = false
                            message = if (task.isSuccessful) {
                                "Login successful!"
                            } else {
                                task.exception?.localizedMessage
                                    ?: "Login failed."
                            }
                        }
                    } else {
                        auth.createUserWithEmailAndPassword(
                            email.trim(),
                            password
                        ).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val profile = userProfileChangeRequest {
                                    displayName = name.trim()
                                }

                                auth.currentUser?.updateProfile(profile)
                                    ?.addOnCompleteListener { profileTask ->
                                        loading = false
                                        message = if (profileTask.isSuccessful) {
                                            "Account created successfully!"
                                        } else {
                                            "Account created, but name could not be saved."
                                        }
                                    } ?: run {
                                    loading = false
                                    message = "Account created, but profile update failed."
                                }
                            } else {
                                loading = false
                                message = task.exception?.localizedMessage
                                    ?: "Account creation failed."
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading
        ) {
            Text(
                if (loading) "Please wait..."
                else if (isLogin) "Login"
                else "Create Account"
            )
        }

        TextButton(
            onClick = {
                isLogin = !isLogin
                message = ""
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading
        ) {
            Text(
                if (isLogin) {
                    "Create a new account"
                } else {
                    "Already have an account? Login"
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = message)
    }
}
