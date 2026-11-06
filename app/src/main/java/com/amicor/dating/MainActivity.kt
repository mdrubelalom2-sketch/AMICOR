package com.amicor.dating

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Rose = Color(0xFFE83E70)
private val Ink = Color(0xFF241B2F)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AmicorApp() }
    }
}

@Composable
fun AmicorApp() {
    var page by remember { mutableStateOf("home") }
    var language by remember { mutableStateOf("English") }
    val isBangla = language == "বাংলা"

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Rose,
            secondary = Color(0xFF8B5CF6),
            background = Color(0xFFFFF8FA),
            surface = Color.White
        )
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFFFF8FA)) {
            when (page) {
                "signup" -> AuthPage(
                    title = if (isBangla) "অ্যাকাউন্ট তৈরি করুন" else "Create your account",
                    button = if (isBangla) "অ্যাকাউন্ট তৈরি করুন" else "Create account",
                    onBack = { page = "home" },
                    onSubmit = { }
                )
                "login" -> AuthPage(
                    title = if (isBangla) "আবার স্বাগতম" else "Welcome back",
                    button = if (isBangla) "লগ ইন" else "Log in",
                    onBack = { page = "home" },
                    onSubmit = { }
                )
                else -> HomePage(
                    isBangla = isBangla,
                    language = language,
                    onLanguage = { language = it },
                    onSignup = { page = "signup" },
                    onLogin = { page = "login" }
                )
            }
        }
    }
}

@Composable
fun HomePage(
    isBangla: Boolean,
    language: String,
    onLanguage: (String) -> Unit,
    onSignup: () -> Unit,
    onLogin: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(104.dp).background(
                Brush.linearGradient(listOf(Rose, Color(0xFF8B5CF6))),
                RoundedCornerShape(30.dp)
            ),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.White, modifier = Modifier.size(54.dp))
        }
        Spacer(Modifier.height(22.dp))
        Text("AMICOR", fontSize = 34.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
        Text(
            if (isBangla) "আপনার পৃথিবীর মানুষের সঙ্গে পরিচিত হন ❤️" else "Meet your world ❤️",
            fontSize = 16.sp, color = Color(0xFF6B6472), textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(30.dp))
        Button(onClick = onSignup, modifier = Modifier.fillMaxWidth().height(54.dp)) {
            Text(if (isBangla) "অ্যাকাউন্ট তৈরি করুন" else "Create account")
        }
        Spacer(Modifier.height(12.dp))
        OutlinedButton(onClick = onLogin, modifier = Modifier.fillMaxWidth().height(54.dp)) {
            Text(if (isBangla) "লগ ইন" else "Log in")
        }
        Spacer(Modifier.height(24.dp))
        Text(if (isBangla) "ভাষা" else "Language", color = Color.Gray)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FilterChip(selected = language == "English", onClick = { onLanguage("English") }, label = { Text("English") })
            FilterChip(selected = language == "বাংলা", onClick = { onLanguage("বাংলা") }, label = { Text("বাংলা") })
        }
        Spacer(Modifier.height(20.dp))
        Text(
            if (isBangla) "বিশ্বজুড়ে সংযোগ, সম্মান ও নিরাপত্তার সঙ্গে।" else "Connect worldwide, with respect and safety.",
            fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AuthPage(title: String, button: String, onBack: () -> Unit, onSubmit: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    val signup = button.lowercase().contains("create") || button.contains("তৈরি")
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        TextButton(onClick = onBack) { Text("← Back") }
        Text(title, fontSize = 27.sp, fontWeight = FontWeight.Bold, color = Ink)
        Spacer(Modifier.height(20.dp))
        if (signup) {
            OutlinedTextField(name, { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
        }
        OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(password, { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())
        if (signup) {
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(confirm, { confirm = it }, label = { Text("Confirm password") }, modifier = Modifier.fillMaxWidth())
        }
        Spacer(Modifier.height(18.dp))
        Button(onClick = onSubmit, modifier = Modifier.fillMaxWidth().height(52.dp)) { Text(button) }
        Text(
            "Demo interface only — account creation and login are not connected yet.",
            modifier = Modifier.padding(top = 14.dp),
            color = Color.Gray, fontSize = 12.sp
        )
    }
}
