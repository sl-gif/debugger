package com.example.debugger

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.debugger.database.DataBase
import com.example.debugger.entity.CustomerTransactionCrossRef
import com.example.debugger.entity.CustomerWithTransaction
import com.example.debugger.entity.CustomerWithTransactions
import com.example.debugger.entity.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TransDetailViewModel(application: Application): AndroidViewModel(application){

    private val repository: Repository
    private val _readAllTans = MutableLiveData<List<CustomerWithTransactions>>()
    var readAllData: LiveData<List<CustomerWithTransactions>> = _readAllTans

    init {
        val transDetailDao = DataBase.getInstance(application).customerDao()
        repository = Repository(transDetailDao)
    }

    var transactionText by mutableStateOf("")

    fun onTransactionTextChange(text: String){
        transactionText = text
    }

    fun insertTransaction(item: Transaction){
        viewModelScope.launch {
            repository.insertTransaction(item)
            transactionText = ""
        }
    }

    fun insertCusTransCrossRef(item: CustomerTransactionCrossRef){
        viewModelScope.launch {
            repository.insertCusTransRef(item)
        }
    }

    fun getCrossRef(name: Int) {
        viewModelScope.launch(Dispatchers.IO) {
           // _readAllTans.postValue(repository.getCrossRef(name))
            Log.d("CUSTOMER WITH TRANSACTION in vm", "this is  ${_readAllTans.value}")

        }

    }

    fun getCustomersWithTransactions(customerId: Int){
        viewModelScope.launch {
            _readAllTans.postValue(repository.getCustomersWithTransactions(customerId))
        }
    }
}

class TransDetailViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(TransDetailViewModel::class.java)){
            return (TransDetailViewModel(application) as T)
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}