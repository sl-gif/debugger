package com.example.debugger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.debugger.ui.customerdetail.CustomerDetailContainer
import com.example.debugger.ui.theme.DEBUGGERTheme
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = MyViewModel(application)
        super.onCreate(savedInstanceState)
        setContent {

            DEBUGGERTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()

                    Box(modifier = Modifier.fillMaxSize()) {

                        val navHost =
                            NavHost(navController = navController, startDestination = "home") {
                                composable("home") {
                                    TabLayout(
                                        viewModel = viewModel,
                                        navController = navController,
                                    )
                                }
                                composable(
                                    "homeDetails/{name}",
                                    arguments = listOf(
                                        navArgument("name") { type = NavType.StringType }
                                    )
                                ) {

                                    val customerName = remember { it.arguments?.getString("name") }
                                    CustomerDetailContainer(name = customerName ?: "",viewModel = viewModel)
                                }
                            }

                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DEBUGGERTheme {
        //   Greeting("Android")

    }
}