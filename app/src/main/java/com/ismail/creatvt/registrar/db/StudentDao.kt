package com.ismail.creatvt.registrar.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDao{

    @Insert
    suspend fun insert(student:Student)

    @Update
    suspend fun update(student: Student)

    @Query("select * from student")
    fun getAllStudents() : LiveData<List<Student>>

    @Query("select * from student where id = :id limit 1")
    fun getStudent(id:Int): LiveData<Student>

    @Delete
    suspend fun delete(student: Student)

}

