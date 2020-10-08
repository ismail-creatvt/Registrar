package com.ismail.creatvt.registrar.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDao{

    //Create
    @Insert
    suspend fun insert(student:Student)

    //Update
    @Update
    suspend fun update(student: Student)

    //Read
    @Query("select * from student")
    fun getAllStudents() : LiveData<List<Student>>

    //Read
    @Query("select * from student where id = :studentId limit 1")
    fun getStudent(studentId:Int): LiveData<Student>

    //Delete
    @Delete
    suspend fun delete(student: Student)

}

