package br.com.fiap.prazocerto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.prazocerto.screens.HomeScreen
import br.com.fiap.prazocerto.screens.LoginScreen
import br.com.fiap.prazocerto.screens.SignupScreen
import br.com.fiap.prazocerto.ui.theme.PrazoCertoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrazoCertoTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        LoginScreen(navController = navController)
                    }

                    composable("signup") {
                        SignupScreen(navController = navController)
                    }

                    composable("home") {
                        HomeScreen(navController = navController)
                    }
                }
            }
        }
    }
}