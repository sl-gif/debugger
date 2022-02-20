package com.example.debugger

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.debugger.common.Layout
import com.example.debugger.data.Analysis
import com.example.debugger.data.Statistics
import com.example.debugger.ui.customerdetail.customerdetailtransactions.TransDetailViewModel
import com.example.debugger.ui.firstscreen.CustomerList
import com.example.debugger.ui.firstscreen.homescreen.ImageWithText
import com.example.debugger.uicommon.ItemsCard
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState

val analysis = listOf<Analysis>(
    Analysis(title = "", type = "", graph = ""),
    Analysis(title = "", type = "", graph = ""),
    Analysis(title = "", type = "", graph = ""),
    Analysis(title = "", type = "", graph = ""),
    Analysis(title = "", type = "", graph = ""),
    Analysis(title = "", type = "", graph = ""),
)


@Composable
fun HomeScreen() {
    HomeScreen(
     openStatistics = {}
    )
}

@Composable
internal fun HomeScreen(
    openStatistics: () -> Unit
) {

    LazyColumn(
        modifier = Modifier.background(Color.LightGray)
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

            Spacer(modifier = Modifier.width(50.dp))
        }

        item {
            SectionHeaderWithHomeCard(
                items = listOf(
                    Statistics(
                      title = "Cash Statistics" ,
                        date = "Today",
                        graph =painterResource(id =R.drawable.artificial_graph )
                    )
                ),
                headertitle = "Statistics",
                modifier = Modifier.fillMaxWidth()
            ) {
               openStatistics()
            }
        }

        item {
            Spacer(Modifier.height(4.dp))
            Divider()
            Text("Analysis")
            Spacer(Modifier.height(4.dp))
            Text("NOT YET IMPLEMENTED!!!")
        }

        item {
            BusinessAnalysisWithHeader()
        }
    }
}

@Composable
private fun SectionHeaderWithHomeCard(
    items: List<Statistics>,
    headertitle: String,
    modifier: Modifier = Modifier,
    onclick: ()-> Unit
) {

    Column(modifier) {
        SectionHeader(
            title = headertitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TextButton(
                modifier = Modifier
                    .alignBy(FirstBaseline)
                    ,
                onClick = onclick) {
                Text(
                    "More",
                    color = Color.Black,
                )
            }
        }
        if(items.isNotEmpty()) {
            StatisticsSection(
                items = items,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }

    }
}


@Composable
private fun StatisticsSection(
    items: List<Statistics>,
    modifier: Modifier = Modifier
) {

            //Remove hard_Coded width and height values, size based on screen size
            HomeCard(
                item = Statistics(
                    graph = painterResource(id =R.drawable.artificial_graph )
                ),
                modifier = modifier
            )
}

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    loading: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    Row(modifier) {
        Spacer(Modifier.width(Layout.bodyMargin))

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = 8.dp)
        )
Spacer(modifier = Modifier.weight(0.7f))
        content()
        Spacer(Modifier.width(Layout.bodyMargin))

    }
}

@Composable
fun BusinessAnalysisWithHeader() {
    Column{
        BusinessAnalysisHeader(
            text = "Business Analysis",
            modifier = Modifier
                .fillMaxWidth()
        )
        BusinessAnalysisSection()

    }
}

@Composable
fun BusinessAnalysisHeader(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
       modifier = modifier
    ){
       Spacer(Modifier.width(Layout.bodyMargin))
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = 8.dp)
        )
    }
}
@Composable
fun BusinessAnalysisSection(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(600.dp)
            .padding(horizontal = Layout.bodyMargin),
        contentColor = Color.LightGray,
        shape = RoundedCornerShape(5.dp)
    ) {

            Column {
                analysis.forEach{ item ->
                    ItemsCard(
                        item = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .height(20.dp)
                    ) {
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
            .padding(bottom = 24.dp,start = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {

        items(shortcuts.size) { item ->
            RoundShapeWithText(
                image = shortcuts[item].image,
                text = "Demo",
                modifier = modifier
                    .size(60.dp)

            )
            Spacer(modifier = modifier.width(24.dp))
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
fun HomeCard(
    modifier: Modifier = Modifier,
    item: Statistics
) {
  Box(
      modifier = modifier,
  ){

//      Card(
//          modifier = modifier,
//          contentColor = Color.LightGray,
//          shape = RoundedCornerShape(8.dp)
//      ) {
      Image(
          painter = item.graph ,
          contentDescription = "graph image",
          modifier = Modifier
              .fillMaxSize()
              .clipToBounds()
      )
          Column {
              Text(
                  fontWeight = FontWeight.Bold,
                  color = Color.Black,
                  textAlign = TextAlign.Start,
                  modifier = modifier
                      .padding(start = 4.dp),
                  text = item.title
              )
              Divider()
              Text(
                  text = item.date,
                  color = Color.Black,
                  textAlign = TextAlign.Start,
                  fontWeight = FontWeight.ExtraBold,
                  fontSize = 15.sp,
                  modifier = modifier
                      .padding(start = 2.dp),

                  )
          }
     /// }
  }
}

@Preview
@Composable
fun PreviewDetail() {
    HomeScreen()
}


