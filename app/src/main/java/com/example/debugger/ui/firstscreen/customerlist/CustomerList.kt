package com.example.debugger.ui.firstscreen

import android.app.Application
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.R
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.debugger.MyViewModel
import com.example.debugger.MyViewModelFactory
import com.example.debugger.TransDetailViewModel
import com.example.debugger.entity.Customers

@Composable
fun CustomerList(
    viewModel: MyViewModel,
    detailViewModel: TransDetailViewModel,
    nav: NavController,
    clicked: Boolean,
    onClick: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val sampleViewModel: MyViewModel = viewModel(
        factory = MyViewModelFactory(context.applicationContext as Application)
    )
//this is a flow not a livedata
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
                    detailViewModel = detailViewModel,
                    nav = nav,
                    getAllCustomers[customer],
                    clicked = clicked,
                    onClick = {
                        detailViewModel.readAllData(getAllCustomers[customer].customerId)
                        nav.navigate("homeDetails/${getAllCustomers[customer].customerName}/${getAllCustomers[customer].customerId}")
                    })
                Log.d("ROOM", "this is customer ${getAllCustomers[customer]}")
            }
        }

        OutlinedTextField(
            value = viewModel.text,
            onValueChange = { viewModel.onChangeText(it) },
            modifier = Modifier.align(Alignment.BottomStart)

        )

        Button(
            onClick = {
                viewModel.insertUser()

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
    detailViewModel: TransDetailViewModel,
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
                detailViewModel.readAllData(customer.customerId)
                nav.navigate("homeDetails/${customer.customerName}/${customer.customerId}")
            },
        //    elevation = 5.dp,
        shape = RoundedCornerShape(5.dp),

        ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = com.example.debugger.R.drawable.ic_launcher_foreground),
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

//This should be reusable for AddCustomer function and Business Analysis Card items
@Composable
fun ItemsCard(
    image: Painter,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(
        modifier
            .clickable {
                 onClick()
            },
        //    elevation = 5.dp,
        shape = RoundedCornerShape(5.dp),

        ) {
        Row(modifier = modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = com.example.debugger.R.drawable.ic_launcher_foreground),
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
                    text = text,
                    modifier = Modifier.weight(1.0f),
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "",
                    modifier = Modifier.weight(1.0f),
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }

    }
}