package com.example.debugger

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.debugger.common.Layout
import com.example.debugger.ui.firstscreen.CustomerList
import com.example.debugger.ui.firstscreen.ItemsCard
import com.example.debugger.ui.firstscreen.homescreen.ImageWithText
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
                    selectedContentColor = Color("#35fdb5".toColorInt())
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

@Composable
fun HomeScreen() {

}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier.background(Color.LightGray)
    ) {
        item {
            DetailLazyRow(
                shortcuts = listOf(
                    ImageWithText(
                        image = painterResource(
                            id = R.drawable.budget
                        ),
                        text = "Budget"
                    ),
                    ImageWithText(
                        image = painterResource(
                            id = R.drawable.calculator
                        ),
                        text = "Calculator"
                    ),
                    ImageWithText(
                        image = painterResource(
                            id = R.drawable.call
                        ),
                        text = "Call"
                    ),
                    ImageWithText(
                        image = painterResource(
                            id = R.drawable.facebook
                        ),
                        text = "Facebook"
                    ),
                    ImageWithText(
                        image = painterResource(
                            id = R.drawable.invoice
                        ),
                        text = "Invoice"
                    ),
                    ImageWithText(
                        image = painterResource(
                            id = R.drawable.savings
                        ),
                        text = "Savings"
                    ),
                    ImageWithText(
                        image = painterResource(
                            id = R.drawable.text
                        ),
                        text = "Text"
                    ),

                    ImageWithText(
                        image = painterResource(
                            id = R.drawable.video_call
                        ),
                        text = "Video Call"
                    ),
                ),
                modifier = Modifier
            )

            Spacer(modifier = modifier.width(24.dp))
        }

        item {
            SectionHeader(
                title = "Statistics",
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text("More")
                }
                HomeCard(
                    modifier = Modifier.height(400.dp)
                )
            }
        }

        item {
            Spacer(modifier.height(4.dp))
            Divider()
            Text("Analysis")
            Spacer(modifier.height(4.dp))
            HomeCard(
                modifier = Modifier
                    .size(200.dp)
            )
        }

        item {
            BusinessAnalysis()
        }
    }
}

@Composable
fun BusinessAnalysis(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 8.dp),
        contentColor = Color.LightGray,
        shape = RoundedCornerShape(5.dp)
    ) {

        Card {

            LazyColumn {
                items(5) {
                    ItemsCard(
                        image = painterResource(id = R.drawable.budget),
                        text = "Inventory Management"
                    ) {

                    }
                }
            }
        }
    }
}

@Composable
fun DetailLazyRow(
    modifier: Modifier = Modifier,
    shortcuts: List<ImageWithText>
) {
    LazyRow(
        modifier = modifier
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {

        items(shortcuts.size) { item ->
            RoundShapeWithText(
                image = shortcuts[item].image,
                text = "Demo",
                modifier = modifier
                    .size(50.dp)

            )
            Spacer(modifier = modifier.width(16.dp))
        }
    }
}

@Composable
fun RoundShapeWithText(
    image: Painter,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = image,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .wrapContentSize()
                .clip(CircleShape)
        )

        Text(
            text,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 3.sp
        )
    }
}

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    loading: Boolean = false,
    content: @Composable () -> Unit
) {
    Row {
        Spacer(modifier.width(Layout.bodyMargin))

        Text(text = title)

        content()
        Spacer(modifier.width(Layout.bodyMargin))

    }
}

@Composable
fun HomeCard(
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentColor = Color.LightGray,
        shape = RoundedCornerShape(8.dp)
    ) {

        Column {
            Text(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .padding(start = 4.dp),
                text = "Net Balance"
            )
            Divider()
            Text(
                text = "30000",
                color = Color.Black,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
                modifier = modifier
                    .padding(start = 2.dp),

                )
        }
    }
}


@Preview
@Composable
fun PreviewDetail() {
    HomeScreen()
}


