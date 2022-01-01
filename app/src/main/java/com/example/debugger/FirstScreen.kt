package com.example.debugger

import android.app.Application
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.debugger.entity.Customers
import com.example.debugger.ui.customerdetail.crossRef
import com.example.debugger.ui.customerdetail.trans
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@Composable
fun TabLayout(
    viewModel: MyViewModel,
    navController: NavController,
   // clicked: Boolean
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Details", "Customer")
    val pagerState = rememberPagerState()
   // var clicked by remember { mutableStateOf(false) }

    Column {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(
                        pagerState = pagerState,
                        tabPositions
                    )
                )
            }) {
            tabs.forEachIndexed { index, text ->
                Tab(
                    selected = index == tabIndex,
                    onClick = { tabIndex = index },
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Text(text = text)
                }

            }

        }
        HorizontalPager(count = tabs.size, state = pagerState) { tabIndex ->
            when (tabIndex) {
                0 -> Text(text = "This is the Detail Screen")
                1 -> CustomerList(
                    viewModel,
                    navController,
                    clicked = viewModel.clicked,
                    onClick = { viewModel.onChangeClick(true) })
            }
        }
//        when (tabIndex) {
//            0 -> Text(text = "This is the Detail Screen")
//            1 -> Text(text = "This is the Customer Screen")
//        }
    }

}
@Composable
fun CustomerList(
    viewModel: MyViewModel,
    nav: NavController,
    clicked: Boolean,
    onClick: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val sampleViewModel: MyViewModel = viewModel(
        factory = MyViewModelFactory(context.applicationContext as Application)
    )

    val getAllCustomers = sampleViewModel.getAllCustomers.observeAsState(listOf()).value
    Log.d("ROOM  from OUTSIDE SCOPE", "this is customer $getAllCustomers")

    Box(modifier = Modifier.fillMaxSize()) {
        //ActionBAr
        //create List Item
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
            items(getAllCustomers.size) { customer ->
                AddCustomer(
                    viewModel = viewModel,
                    nav = nav,
                    getAllCustomers[customer],
                    clicked = clicked,
                    onClick = onClick
                )
                Log.d("ROOM", "this is customer ${getAllCustomers[customer]}")
            }
        }

        OutlinedTextField(
            value = viewModel.text,
            onValueChange = { viewModel.onChangeText(it) },
            modifier = Modifier.align(Alignment.BottomStart)

        )

        Button(
            onClick = { viewModel.insertUsers()
                viewModel.insertTransaction(trans)
                viewModel.insertCusTransCrossRef(crossRef)
                      },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Text("Click")
        }

    }
}

@Composable
fun AddCustomer(
    viewModel: MyViewModel,
    nav: NavController,
    customer: Customers,
    clicked: Boolean,
    onClick: (Boolean) -> Unit
) {
    Log.d("ADDCUSTOMER", "this is customer ${customer.customerName} and ${customer.customerType}")

    Card(
        Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(50.dp)
            .clickable {
                nav.navigate("homeDetails/${customer.customerName}") },
        //    elevation = 5.dp,
        shape = RoundedCornerShape(5.dp),

        ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .wrapContentSize()
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = customer.customerName,
                    modifier = Modifier.weight(1.0f),
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = customer.customerType,
                    modifier = Modifier.weight(1.0f),
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }

    }
}


