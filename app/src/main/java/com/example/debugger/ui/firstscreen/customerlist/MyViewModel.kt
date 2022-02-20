package com.example.debugger

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.debugger.database.DataBase
import com.example.debugger.entity.*
import com.example.debugger.repository.Repository
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {

    val getAllCustomers: LiveData<List<Customers>>
    val getAllCross: LiveData<List<CustomerTransactionCrossRef>>
    val getAllTransactions: LiveData<List<Transaction>>

    var transWithCus = MutableLiveData<List<TransactionWithCustomer>>()

    private val repository: Repository

    init {
        val customerDao = DataBase.getInstance(application).customerDao()
        repository = Repository(customerDao)
        getAllCustomers = repository.getAllCustomers
        getAllTransactions = repository.getAllTransactions
        getAllCross = repository.getAllCross

    }

    var clicked by mutableStateOf(false)
    var transactionText by mutableStateOf("")

    fun onTransactionTextChange(text: String){
        transactionText = text
    }

    fun onChangeClick(state : Boolean){
        clicked=state
    }

    var text by mutableStateOf("")
        private set

    fun onChangeText(newText: String) {
        text = newText
    }

    var type by mutableStateOf("student")
        private set

    fun onChangeType(newType: String) {
        type = newType
    }

    fun insertUser() {
        if (text.isBlank() || type.isBlank()) {
            return
        }
        viewModelScope.launch { repository.insertCustomer(Customers(text, "student")) }
        text = ""
    }

     fun insertUsers(){
      viewModelScope.launch {
          repository.insertCustomers(mutableListOf(
              Customers("victor","student", customerId = 0),
              Customers("okeagu","student"),
              Customers("stephen","student"),
          ))
          text = ""
          type = ""
      }

}

    fun getTransWithCus(name: Int){
        viewModelScope.launch {
            transWithCus.postValue(repository.getTransWithCus(name))
        }

    }
}

class MyViewModelFactory(
    private val application: Application
    ):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}