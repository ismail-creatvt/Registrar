package com.ismail.creatvt.registrar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_student.setOnClickListener {
            startActivity(AddStudentActivity::class.java)
        }

        manage_students.setOnClickListener {
            startActivity(ManageStudentsActivity::class.java)
        }

        about_us.setOnClickListener {
            startActivity(AboutUsActivity::class.java)
        }

    }

    private fun startActivity(activity: Class<out AppCompatActivity>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

}