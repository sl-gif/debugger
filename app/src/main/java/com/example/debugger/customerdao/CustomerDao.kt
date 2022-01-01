package com.example.debugger.customerdao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.debugger.entity.CustomerTransactionCrossRef
import com.example.debugger.entity.CustomerWithTransaction
import com.example.debugger.entity.Customers
import com.example.debugger.entity.TransactionWithCustomer

@Dao
interface CustomerDao {

    @Query("SELECT * FROM Customers")
    fun getAllCustomers(): LiveData<List<Customers>>

    @Query("SELECT * FROM CustomerTransactionCrossRef")
    fun getAllCross(): LiveData<List<CustomerTransactionCrossRef>>


    @Query("SELECT * FROM `Transaction`")
    fun getAllTransactions(): LiveData<List<com.example.debugger.entity.Transaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customers)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: List<com.example.debugger.entity.Transaction>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomerTransactionCrossRef(
        crossRef: List< CustomerTransactionCrossRef>
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomers(customers: MutableList<Customers>)

    @Delete
    suspend fun deleteCustomer(customer: Customers)

    @Query("DELETE FROM customers")
    suspend fun deleteCustomers()

//Complete this thank you!!!
    @Transaction
    @Query("SELECT * FROM `Transaction` WHERE transactionId = :transactionId")
    suspend fun getCustomersOfTransaction(
       transactionId: Int
    ): List<TransactionWithCustomer>

    @Transaction
    @Query("SELECT * FROM customers WHERE customerId = :customerId")
    suspend fun getTransactionOfCustomer(
        customerId: Int
    ): List<CustomerWithTransaction>


}