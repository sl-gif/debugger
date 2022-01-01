package com.example.debugger.ui.customerdetail

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.debugger.MyViewModel
import com.example.debugger.entity.CustomerTransactionCrossRef
import com.example.debugger.entity.Transaction
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


val crossRef = listOf(
    CustomerTransactionCrossRef(0,0),
    CustomerTransactionCrossRef(0,1),
    CustomerTransactionCrossRef(1,2),
    CustomerTransactionCrossRef(2,1),
    CustomerTransactionCrossRef(1,0),
    CustomerTransactionCrossRef(2,2),
)

val trans = listOf(
    Transaction("cash","demo",customerOwnerId =0, transactionId = 0),
    Transaction("credit","demo",customerOwnerId =1),
    Transaction("debt","demo",customerOwnerId =2),
    Transaction("cash","demo",customerOwnerId =2),
)

@Composable
fun CustomerDetailContainer(
    name: String,
    viewModel: MyViewModel
) {
//    viewModel.insertTransaction(trans)
//    viewModel.insertCusTransCrossRef(crossRef)
    val scope = rememberCoroutineScope()
    val getAllRecord = viewModel.readAllData.observeAsState(listOf()).value
    val getRecord = viewModel.getAllTransactions.observeAsState(listOf()).value
    val getCross = viewModel.getAllCross.observeAsState(initial = listOf() ).value
    val transWithCus = viewModel.transWithCus.observeAsState(initial = listOf()).value

//    Log.d("CUSTOMER WITH TRANSACTION", "this is  $getAllRecord")
    Log.d("ALL TRANSACTION", "this is  $getRecord")
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
//        if (getAllRecord.isNotEmpty()){
//            LazyColumn {
//                items(getAllRecord.size) { item ->
//                    CustomerDetail(
//                        viewModel = viewModel,
//                        text = getAllRecord[item].transaction[0].transactionDetail,
//                        name = name
//                    )
//                }
//            }
//    }

    OutlinedTextField(
        value = viewModel.transactionText,
        onValueChange = { viewModel.onTransactionTextChange(it)},
        modifier = Modifier.align(Alignment.BottomStart)

    )

        Button(
            onClick = {



            },
            modifier = Modifier.align(Alignment.BottomCenter)){}

    Button(
        onClick = {
            scope.launch {
                viewModel.getCrossRef(1)
                viewModel.getTransWithCus(1)
                Log.d("BTN CLICKEDuy", "this is  $getAllRecord")
                Log.d("BTN CLICKEDuy new", "this is  $transWithCus")
//            viewModel.insertTransaction()
//            viewModel.insertCusTransCrossRef()
           // Log.d("BTN CLICKED", "this is  $getAllRecord")
                }

                  },
        modifier = Modifier.align(Alignment.BottomEnd)) {}
}
}
//
//@Composable
//fun CustomerDetail(
//    viewModel: MyViewModel,
//    text: String,
//    name: String
//) {
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .padding(5.dp),
//            shape = RoundedCornerShape(3.dp)
//        ) {
//           val getRecord = viewModel.readAllData
//           Text("$text is the transaction")
//
//        }
//    }
//}