package com.example.debugger.ui.customerdetail.BottomSheet

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.debugger.R
import com.example.debugger.entity.Transaction
import com.example.debugger.ui.customerdetail.customerdetailtransactions.CustomerDetailContainer
import com.example.debugger.ui.customerdetail.customerdetailtransactions.TransDetailViewModel
import kotlinx.coroutines.launch


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
                title = stringResource(id = R.string.drop_down_title)
            )
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

@Composable
fun DropDownHeader(
    modifier: Modifier = Modifier,
    title: String,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        // horizontalArrangement = Arrangement.SpaceBetween
    ) {
        painterResource(id = R.drawable.ic_launcher_background)

        Text(
            text = title,
            modifier = modifier
                .padding(start = 20.dp)
                .background(Color.White)
                .width(24.dp),
            fontSize = 12.sp,
            color = Color.Black
        )
        TextButton(
            modifier = Modifier.background(Color.White),
            onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(id = R.string.Header_Save),
                color = Color.Green
            )
        }
    }

    @Composable
    fun DropDownMenuTransDetail(
        modifier: Modifier = Modifier
    ) {
        Column(

        ) {
            TextField(
                value = "",
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text("Amount") },
                modifier = modifier
                    .fillMaxWidth()

            )
            TextField(
                value = "",
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                label = { Text("Transaction Note") },
                singleLine = false,
                modifier = modifier
                    .fillMaxWidth()

            )
        }
    }
}

@Preview
@Composable
fun AppBar() {
    DropDownHeader(title = "")
}


