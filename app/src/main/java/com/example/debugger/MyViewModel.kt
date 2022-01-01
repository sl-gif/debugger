package com.example.debugger

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.debugger.database.DataBase
import com.example.debugger.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {

    val getAllCustomers: LiveData<List<Customers>>
    val getAllCross: LiveData<List<CustomerTransactionCrossRef>>
    val getAllTransactions: LiveData<List<Transaction>>

    private val _readAllTans = MutableLiveData<List<CustomerWithTransaction>>()
    var readAllData: LiveData<List<CustomerWithTransaction>> = _readAllTans
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
        viewModelScope.launch { repository.insertCustomer(Customers(text, type)) }
        text = ""
        type = ""
    }

     fun insertUsers(){
      viewModelScope.launch {
          repository.insertCustomers(mutableListOf(
              Customers("victor","student"),
              Customers("okeagu","student"),
              Customers("stephen","student"),
          ))
          text = ""
          type = ""
      }

}

    fun insertTransaction(item: List<Transaction>){
        viewModelScope.launch {
            repository.insertTransaction(item)
//            repository.insertTransaction(Transaction(
//                "cash","demo"
//            ))
//            repository.insertTransaction(Transaction(
//                "credit","demo"
//            ))
//            repository.insertTransaction(Transaction(
//                "debt","demo"
//            ))
//            insertCusTransCrossRef()
//        //    getCrossRef("victor")
//
      }
    }

    fun insertCusTransCrossRef(item: List<CustomerTransactionCrossRef>){
        viewModelScope.launch {
            repository.insertCusTransRef(item)
//            repository.insertCusTransRef(
//                CustomerTransactionCrossRef("victor","cash")
//            )
//            repository.insertCusTransRef(
//                CustomerTransactionCrossRef("victor","credit")
//            )
//            repository.insertCusTransRef(
//                CustomerTransactionCrossRef("okeagu","cash")
//            )
//            repository.insertCusTransRef(
//                CustomerTransactionCrossRef("stephen","debt")
//            )
//            repository.insertCusTransRef(
//                CustomerTransactionCrossRef("stephen","debt")
//            )

             // transactionText = ""
        }
    }

    fun getCrossRef(name: Int) {
     viewModelScope.launch(Dispatchers.IO) {
         _readAllTans.postValue(repository.getCrossRef(name))
         Log.d("CUSTOMER WITH TRANSACTION in vm", "this is  ${_readAllTans.value}")
         transactionText = ""
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