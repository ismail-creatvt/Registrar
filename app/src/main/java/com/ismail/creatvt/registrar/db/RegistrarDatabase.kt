package com.ismail.creatvt.registrar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Student::class], version = 1)
@TypeConverters(value = [DateConverter::class])
abstract class RegistrarDatabase: RoomDatabase() {

    abstract fun getStudentDao():StudentDao

    companion object{

        private var instance:RegistrarDatabase?=null

        fun getInstance(context: Context): RegistrarDatabase {
            if(instance == null){
                instance = Room.databaseBuilder(
                    context,
                    RegistrarDatabase::class.java,
                    "student_records"
                ).build()
            }
            return instance!!
        }

    }

}