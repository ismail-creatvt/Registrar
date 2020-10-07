package com.ismail.creatvt.registrar

import com.ismail.creatvt.registrar.db.Student

interface StudentActionListener {

    fun onDelete(student:Student)

    fun onEdit(student: Student)

}