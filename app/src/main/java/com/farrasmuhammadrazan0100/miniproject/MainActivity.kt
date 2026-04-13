package com.farrasmuhammadrazan0100.miniproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.farrasmuhammadrazan0100.miniproject.navigation.SetupNavGraph
import com.farrasmuhammadrazan0100.miniproject.ui.theme.MiniProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniProjectTheme {
                SetupNavGraph()
                }
            }
        }
    }
