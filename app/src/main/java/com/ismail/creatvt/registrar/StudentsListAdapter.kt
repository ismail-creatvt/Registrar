package com.ismail.creatvt.registrar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.ismail.creatvt.registrar.db.Student
import kotlinx.android.synthetic.main.student_item_layout.view.*

class StudentsListAdapter(var studentsData: LiveData<List<Student>>, lifeCycleOwner:LifecycleOwner)
    : RecyclerView.Adapter<StudentsListAdapter.StudentItemViewHolder>() {

    var students:ArrayList<Student> = arrayListOf()

    var studentActionListener:StudentActionListener?=null

    init {
        studentsData.observe(lifeCycleOwner){
            students.clear()
            students.addAll(it)
            notifyDataSetChanged()
        }
    }

    class StudentItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentItemViewHolder {
        //Create an object of layout iflater
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.student_item_layout, parent, false)

        return StudentItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentItemViewHolder, position: Int) {
        val view = holder.itemView
        val student = students[position]
        view.name_text.text = student.name
        view.class_text.text = student.className

        view.edit_button.setOnClickListener {
            studentActionListener?.onEdit(student)
        }

        view.delete_button.setOnClickListener {
            studentActionListener?.onDelete(student)
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }
}