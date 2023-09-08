package com.example.whatsapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {

    @Composable
    fun MainScreen() {
        Text(text = "Bem vindo a tela inicial")
    }

    @Preview(showBackground = true)
    @Composable
    fun Main() {
        MainScreen()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}
