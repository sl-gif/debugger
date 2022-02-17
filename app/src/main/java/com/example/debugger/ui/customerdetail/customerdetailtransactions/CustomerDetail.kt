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
