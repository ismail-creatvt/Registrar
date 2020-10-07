package com.ismail.creatvt.registrar

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ismail.creatvt.registrar.db.RegistrarDatabase
import com.ismail.creatvt.registrar.db.Student
import com.ismail.creatvt.registrar.db.StudentDao
import kotlinx.android.synthetic.main.activity_manage_students.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ManageStudentsActivity : AppCompatActivity(), StudentActionListener {

    var studentsDao:StudentDao?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_students)

        //Get the instance of db
        val db = RegistrarDatabase.getInstance(this)

        studentsDao = db.getStudentDao()

        val students = studentsDao!!.getAllStudents()

        //create an instance of adapter
        val adapter = StudentsListAdapter(students, this)

        adapter.studentActionListener = this

        //set the adapter to the recyclerView
        students_list.adapter = adapter

        //set the layout manager as LinearLayoutManager to show it in a vertical order
        students_list.layoutManager = LinearLayoutManager(this)

    }

    override fun onDelete(student: Student) {
        AlertDialogManager.showConfirmation(this, R.string.delete_confirmation){
            CoroutineScope(IO).launch {
                studentsDao?.delete(student)
            }
        }
    }

    override fun onEdit(student: Student) {
        val intent = Intent(this, AddStudentActivity::class.java)
        intent.putExtra("studentId", student.id)
        startActivity(intent)
    }
}