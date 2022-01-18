package com.example.debugger.ui.customerdetail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.debugger.MyViewModel
import com.example.debugger.TransDetailViewModel
import com.example.debugger.entity.CustomerTransactionCrossRef
import com.example.debugger.entity.Transaction
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*


@ExperimentalMaterialApi
@Composable
fun CustomerDetailContainer(
    name: String,
    id: Int,
    viewModel: TransDetailViewModel
) {

    val scope = rememberCoroutineScope()
    val getAllRecord = viewModel.readAllData.observeAsState(listOf()).value
//    val getRecord = viewModel.getAllTransactions.observeAsState(listOf()).value
//    val getCross = viewModel.getAllCross.observeAsState(initial = listOf()).value
//    val transWithCus = viewModel.transWithCus.observeAsState(initial = listOf()).value

 //   Log.d("ALL TRANSACTION", "this is  $getRecord")
    Log.d("ALL Trans from cus", "this is  $getAllRecord")
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        if (getAllRecord.isNotEmpty()) {
            Log.d("all freaking transaction..", "this   ${getAllRecord.first().transactions.size}")
            LazyColumn {
                items(getAllRecord[0].transactions) { item ->
                    CustomerDetail(
                        viewModel = viewModel,
                        text = item.transactionType,
                        name = name
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CustomerDetail(
    viewModel: TransDetailViewModel,
    text: String,
    name: String
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(5.dp),
            shape = RoundedCornerShape(3.dp)
        ) {
            Text(text = text)

        }
    }
}


@ExperimentalMaterialApi
@Composable
fun BottomSheet(
    customerName: String,
    customerId: Int,
    viewModel: TransDetailViewModel
) {

    val bottomState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden )

    val coroutineScope = rememberCoroutineScope()
    ModalBottomSheetLayout(
      // backgroundColor = Color.Black,
        sheetState = bottomState,
        sheetContent = {
                       BottomSheetContent(
                           customerId = customerId,
                           viewModel = viewModel)

        },
      //  sheetPeekHeight = 0.dp
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            CustomerDetailContainer(
                name = customerName,
                id = customerId,
                viewModel = viewModel
            )

            Button(
                modifier = Modifier
                    .align(Alignment.BottomStart),
                onClick = {
                    coroutineScope.launch {

                        if (!bottomState.isVisible) {
                            bottomState.show()
                        } else {
                            bottomState.hide()
                        }
                    }
                }) {
                Text("Collapse or Expand")
            }
        }

    }
}

@Composable
fun BottomSheetContent(
    customerId: Int,
    viewModel: TransDetailViewModel
) {
    var selectedIndex = remember { mutableStateOf(0) }
    var expanded = remember { mutableStateOf(false) }
    val items = listOf(
        "Cash", "Credit",
        "Discount", "Premium"
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {


        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .padding(all = 0.dp)
            ) {
                Button(
                    onClick = { expanded.value = true },
                    modifier = Modifier.width(100.dp),
                    border = BorderStroke(width = 1.dp, color = Color.Red)
                ) {
                    Text(text = "")
                }

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier
                        .background(Color.Blue)
                        .border(BorderStroke(width = 1.dp, color = Color.Red))
                        .shadow(elevation = 2.dp)
                        .width(100.dp)
                ) {
                    items.forEachIndexed { index, s ->
                        DropdownMenuItem(onClick = {
                            selectedIndex.value = index
                            expanded.value = false
                        }) {
                            Text(text = s)
                        }
                    }
                }
                OutlinedTextField(
                    value = viewModel.transactionText,
                    onValueChange = { viewModel.onTransactionTextChange(it) }
                )
                Button(onClick = {
                    viewModel.insertTransaction(
                       Transaction(
                           viewModel.transactionText,
                           "demo",
                           customerOwnerId = 1
                       )
                    )
//                    viewModel.insertCusTransCrossRef(
//                        CustomerTransactionCrossRef(
//                            customerId,
//                            1
//                        )
//                    )
                       viewModel.getCustomersWithTransactions(1)
                        Log.d("SELECTED INDEX", "this   ${selectedIndex.value} cus ID id $customerId")

                }) {
                    Text("Click Me")
                }
            }
        }

    }
}

//@Composable
//fun DropDownMenu() {
//    var expanded = remember{ mutableStateOf(false)}
//    val items = listOf(
//        "Cash","Credit","Discount","Premium"
//    )
//
//    var selectedIndex = remember{ mutableStateOf(0)}
//
//
//    Box(modifier = Modifier.fillMaxSize()){
//
//        Column(
//            modifier = Modifier
//                .align(alignment = Alignment.Center)
//                .padding(all = 0.dp)
//        ) {
//            Button(
//                onClick = { expanded.value = true },
//                modifier = Modifier.width(200.dp),
//                border = BorderStroke(width = 1.dp, color = Color.Red)
//            ) {
//                Text(text = "")
//            }
//
//            DropdownMenu(expanded = expanded.value ,
//                onDismissRequest = { expanded.value = false},
//                modifier = Modifier
//                    .background(Color.White)
//                    .border(BorderStroke(width = 1.dp, color = Color.Red))
//                    .shadow(elevation = 2.dp)
//                    .width(2000.dp)
//                ) {
//                items.forEachIndexed { index, s ->
//                    DropdownMenuItem(onClick = {
//                        selectedIndex.value = index
//                        expanded.value = false
//                    }) {
//                        Text(text = s)
//                    }
//                }
//            }
//        }
//    }
//}