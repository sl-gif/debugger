package com.example.debugger.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.debugger.customerdao.CustomerDao
import com.example.debugger.entity.CustomerTransactionCrossRef
import com.example.debugger.entity.Customers
import com.example.debugger.entity.Transaction

@Database(
    entities = [
        Customers::class,
        Transaction::class,
        CustomerTransactionCrossRef::class
               ],
    version = 1,
    exportSchema = false)
abstract class DataBase: RoomDatabase() {

    abstract fun customerDao(): CustomerDao

    companion object{
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase {
            val sampleInstance = INSTANCE
            if(sampleInstance != null){
                return sampleInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                DataBase::class.java,
                "sample_database").fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}