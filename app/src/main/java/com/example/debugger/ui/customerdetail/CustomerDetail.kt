package com.example.debugger.ui.customerdetail

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.debugger.R
import com.example.debugger.TransDetailViewModel
import com.example.debugger.entity.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun CustomerDetailContainer(
    name: String,
    id: Int,
    viewModel: TransDetailViewModel
) {

    val scope = rememberCoroutineScope()

    val getAllRecord by rememberFlowWithLifeCycle(viewModel.readAllData(id))
        .collectAsState(initial = listOf())


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
                        name = name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(5.dp)
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
    name: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(3.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween
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

    val bottomState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val coroutineScope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        // backgroundColor = Color.Black,
        sheetState = bottomState,
        sheetContent = {
            BottomSheetContent(
                customerId = customerId,
                viewModel = viewModel
            )
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
    val categories = listOf(
        "Cash", "Credit",
        "Discount", "Premium",
        "Expense", "Income"
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {


        Column(
            modifier = Modifier
                .padding(all = 0.dp)
                .fillMaxWidth()
        ) {

            DropDownHeader(
                modifier = Modifier.fillMaxWidth(),
                firstIcon = { Icons.Filled.Close },
                title = stringResource(id = R.string.drop_down_title)
            ) {
                TextButton(
                    modifier = Modifier.background(Color.White),
                    onClick = { /*TODO*/ }) {
                    Text(
                        text = stringResource(id = R.string.Header_Save),
                        color = Color.Green
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

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
                categories.forEachIndexed { index, s ->
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
                        customerOwnerId = customerId
                    )
                )
                viewModel.readAllData(customerId)
                Log.d("SELECTED INDEX", "this   ${selectedIndex.value} cus ID id $customerId")

            }) {
                Text("Click Me")
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


@Composable
fun DropDownHeader(
    modifier: Modifier,
    title: String,
    firstIcon: @Composable RowScope.(modifier: Modifier) -> Unit,
    secondIcon: @Composable RowScope.(modifier: Modifier) -> Unit,
) {

    Row(
        modifier = modifier
            .fillMaxWidth(),
        // horizontalArrangement = Arrangement.SpaceBetween
    ) {
        firstIcon( modifier =  modifier.weight(1f))
        Text(
                text = title
        )
        secondIcon (modifier = modifier)
    }
}

@Composable
fun <T> rememberFlowWithLifeCycle(
   flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}

@Preview()
@Composable
fun AppBar() {
    DropDownHeader(
        modifier = Modifier.fillMaxWidth(),
        firstIcon = { Icons.Filled.Close },
        title = stringResource(id = R.string.drop_down_title)
    ) {
        TextButton(
            modifier = Modifier.background(Color.White),
            onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(id = R.string.Header_Save),
                color = Color.Green
            )
        }
    }
}