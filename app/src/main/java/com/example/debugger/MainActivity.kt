package com.example.debugger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.debugger.ui.customerdetail.BottomSheet.BottomSheet
import com.example.debugger.ui.theme.DEBUGGERTheme
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(this,MyViewModelFactory(application)) [MyViewModel::class.java]
        val transDetailViewModel = ViewModelProvider(this,TransDetailViewModelFactory(application)) [TransDetailViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContent {

            DEBUGGERTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    val navController = rememberNavController()

                    Box(modifier = Modifier.fillMaxSize()) {

                        val navHost =
                            NavHost(navController = navController, startDestination = "home") {
                                composable("home") {
                                    TabLayout(
                                        viewModel = viewModel,
                                        detailViewModel = transDetailViewModel,
                                        navController = navController,
                                    )
                                }
                                composable(
                                    "homeDetails/{name}/{id}",
                                    arguments = listOf(
                                        navArgument("name") { type = NavType.StringType },
                                        navArgument("id"){type = NavType.IntType}
                                    )
                                ) {

                                    val customerName = remember { it.arguments?.getString("name") }
                                    val customerId = remember { it.arguments?.getInt("id") }
//                                    CustomerDetailContainer(
//                                        name = customerName ?: "",
//                                        id = customerId!! ,
//                                        viewModel = viewModel
//                                    )
                                    BottomSheet(
                                        customerName =customerName ?: ""  ,
                                        customerId = customerId!! ,
                                        viewModel = transDetailViewModel
                                    )
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