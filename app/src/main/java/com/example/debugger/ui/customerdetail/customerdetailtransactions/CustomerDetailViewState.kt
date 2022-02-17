package com.example.debugger.ui.customerdetail.customerdetailtransactions

import androidx.compose.runtime.Immutable

@Immutable
internal data class CustomerDetailViewState(
   val state: Int
){
    companion object{
        val Empty = CustomerDetailViewState
    }
}
