package com.example.debugger

import androidx.lifecycle.LiveData
import com.example.debugger.customerdao.CustomerDao
import com.example.debugger.entity.*
import kotlinx.coroutines.flow.Flow

class Repository(private val customerDao: CustomerDao) {

    val getAllCustomers: LiveData<List<Customers>> = customerDao.getAllCustomers()
    val getAllCross: LiveData<List<CustomerTransactionCrossRef>>
    = customerDao.getAllCross()
    val getAllTransactions: LiveData<List<com.example.debugger.entity.Transaction>>
    = customerDao.getAllTransactions()

    suspend fun insertCustomer(customer: Customers){
        customerDao.insertCustomer(customer = customer)
    }

    suspend fun insertTransaction(transaction: Transaction){
        customerDao.insertTransaction(transaction = transaction)
    }

    suspend fun insertCusTransRef(crossRef:  CustomerTransactionCrossRef){
        customerDao.insertCustomerTransactionCrossRef(crossRef = crossRef)
    }

    suspend fun insertCustomers(customers: MutableList<Customers>){
        customerDao.insertCustomers(customers = customers)
    }

    suspend fun deleteCustomer(customer: Customers){
        customerDao.deleteCustomer(customer = customer)
    }

    suspend fun deleteCustomers(){
        customerDao.deleteCustomers()
    }

    suspend  fun getCrossRef(name: Int): List<CustomerWithTransaction>{
       return customerDao.getTransactionOfCustomer(name)
    }

    suspend  fun getTransWithCus(name: Int): List<TransactionWithCustomer>{
        return customerDao.getCustomersOfTransaction(name)
    }

    suspend fun getCustomersWithTransactions(customerId: Int): List<CustomerWithTransactions>{
        return customerDao.getCustomersWithTransactions(customerId = customerId)
    }

}