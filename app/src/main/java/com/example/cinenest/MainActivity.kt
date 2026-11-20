package com.example.cinenest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.cinenest.ui.navigation.CineNestNavHost
import com.example.cinenest.ui.theme.CineNestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineNestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CineNestNavHost(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}