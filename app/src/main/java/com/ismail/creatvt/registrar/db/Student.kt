package com.ismail.creatvt.registrar.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

// Entity == Table
@Entity
data class Student(
    @PrimaryKey(autoGenerate = true) var id:Int = 0,
    var name:String,
    var mobile:String,
    var email:String,
    var isMale:Boolean,
    var dateOfBirth:Date,
    var className:String
)