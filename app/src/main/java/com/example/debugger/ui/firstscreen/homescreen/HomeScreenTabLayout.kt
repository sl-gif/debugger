package com.example.debugger.ui.firstscreen.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.debugger.HomeScreen
import com.example.debugger.MyViewModel
import com.example.debugger.ui.customerdetail.customerdetailtransactions.TransDetailViewModel
import com.example.debugger.ui.firstscreen.CustomerList
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState


@ExperimentalPagerApi
@Composable
fun TabLayout(
    viewModel: MyViewModel,
    detailViewModel: TransDetailViewModel,
    navController: NavController,
    // clicked: Boolean
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Details", "Customer")
    val pagerState = rememberPagerState()

    Column {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .pagerTabIndicatorOffset(
                            pagerState = pagerState,
                            tabPositions
                        )
                        .background(Color.White)
                )
            }) {
            tabs.forEachIndexed { index, text ->
                Tab(
                    selected = index == tabIndex,
                    onClick = { tabIndex = index },
                    modifier = Modifier
                        .padding(10.dp),
                    selectedContentColor = Color(0xFF35fdb5)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(4.dp),
                        text = text,
                        color = Color.Black
                    )
                }

            }

        }
        HorizontalPager(count = tabs.size, state = pagerState) { tabIndex ->
            when (tabIndex) {
                0 -> HomeScreen()
                1 -> CustomerList(
                    viewModel,
                    detailViewModel = detailViewModel,
                    nav = navController,
                    clicked = viewModel.clicked,
                    onClick = { viewModel.onChangeClick(true) })
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview()
@Composable
fun TabLayoutPreview() {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Details", "Customer")
    val pagerState = rememberPagerState()

    Column {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .pagerTabIndicatorOffset(
                            pagerState = pagerState,
                            tabPositions
                        )
                        .background(Color.White)
                )
            }) {
            tabs.forEachIndexed { index, text ->
                Tab(
                    selected = index == tabIndex,
                    onClick = { tabIndex = index },
                    modifier = Modifier
                        .padding(10.dp),
                    selectedContentColor = Color(0xFF35fdb5)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(4.dp),
                        text = text,
                        color = Color.Black
                    )
                }

            }

        }
        HorizontalPager(count = tabs.size, state = pagerState) { tabIndex ->
            when (tabIndex) {
                0 -> HomeScreen()
                1 -> Text("")
            }
        }
    }
}