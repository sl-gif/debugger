package com.example.debugger.entity

import androidx.room.*


@Entity
data class Customers(

    val customerName: String,

    val customerType: String,

    @PrimaryKey(autoGenerate = true)
    val customerId: Int = 0
)

@Entity
data class Transaction(

    val transactionType: String,

    val transactionDetail: String,

    @PrimaryKey(autoGenerate = true)
    val transactionId: Int= 0,

    val customerOwnerId: Int

)
//@Entity(
//    tableName = "cross",
//    primaryKeys =["customerId","transactionId"],
//    foreignKeys = [
//        ForeignKey(
//            parentColumns = ["name"],
//            childColumns = ["customerId"],
//            entity = Customers::class,
//        ),
//        ForeignKey(
//            parentColumns = ["transactionType"],
//            childColumns = ["transactionId"],
//            entity = Transaction::class
//        )
//    ]
//)
@Entity(primaryKeys =["customerId","transactionId"])
data class CustomerTransactionCrossRef(
    val customerId: Int,
    val transactionId: Int
)

data class CustomerWithTransaction(
    @Embedded val customers: Customers,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "transactionId",
      //  entity = Transaction::class,
        associateBy = Junction(CustomerTransactionCrossRef::class)
    )
    val transaction: List<Transaction>
)

data class TransactionWithCustomer(
    @Embedded val transaction: Transaction,
    @Relation(
        parentColumn = "transactionId",
        entityColumn = "customerId",
     //   entity = Customers::class,
        associateBy = Junction(CustomerTransactionCrossRef::class)
    )
    val customers: List<Customers>
)


