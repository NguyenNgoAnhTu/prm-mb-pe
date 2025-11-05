package com.example.se1720_nguyenngoanhtu_se181559

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.se1720_nguyenngoanhtu_se181559.navigation.MealNavGraph
import com.example.se1720_nguyenngoanhtu_se181559.ui.theme.SE1720NguyenNgoAnhTuSE181559Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SE1720NguyenNgoAnhTuSE181559Theme {
                val navController = rememberNavController()
                MealNavGraph(navController = navController)
            }
        }
    }
}