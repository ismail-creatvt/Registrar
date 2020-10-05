package com.ismail.creatvt.registrar

import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import com.ismail.creatvt.registrar.db.RegistrarDatabase
import com.ismail.creatvt.registrar.db.Student
import kotlinx.android.synthetic.main.activity_add_student.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Language
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class AddStudentActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        setUpClassDropDown()

        submit.setOnClickListener(this::onSubmit)
        cancel.setOnClickListener {
            showCancelConfirmationDialog()
        }

        date_field.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val date = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val datePicker = DatePickerDialog(
            this,
            this,
            year,
            month,
            date
        )
        val milliInOneYear = 365 * 24 * 60 * 60 * 1000.toLong()
        val milliInThirtyYears = (30 * milliInOneYear)
        val milliInTwelveYears = (12 * milliInOneYear)
        datePicker.datePicker.minDate = calendar.timeInMillis - milliInThirtyYears
        datePicker.datePicker.maxDate = calendar.timeInMillis - milliInTwelveYears
        datePicker.show()
    }

    private fun onSubmit(view: View) {
        val student = getValidatedStudent()
        if (student != null) {
            saveDataToDb(student)
            finish()
        }
    }

    private fun saveDataToDb(student: Student) {
        //Get database object and Dao object
        val db = RegistrarDatabase.getInstance(this)
        val studentDao = db.getStudentDao()

        //Create scope of IO Dispatcher to run db operations asynchronously
        val scope = CoroutineScope(IO)

        //launch coroutine to insert student data
        scope.launch {
            studentDao.insert(student)
        }
    }

    /**
     * Checks if the user has entered valid data
     */
    private fun getValidatedStudent(): Student? {
        val name = name_field.text.toString()
        val email = email_field.text.toString()
        val mobile = mobile_field.text.toString()
        val dateOfBirth = date_field.text.toString()
        val isMale = male_option.isChecked
        val className = class_spinner.selectedItem.toString()

        if (name.isEmpty()) {
            name_field.error = "Please enter name!"
            return null
        }
        name_field.error = null

        if (isEmailInvalid(email)) {
            email_field.error = "Please enter valid email!"
            return null
        }
        email_field.error = null

        if (isMobileInvalid(mobile)) {
            mobile_field.error = "Please enter valid mobile!"
            return null
        }
        mobile_field.error = null

        if (dateOfBirth.isEmpty()) {
            date_field.error = "Please enter date of birth!"
            return null
        }
        date_field.error = null

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val dob = dateFormat.parse(dateOfBirth) ?: return null

        return Student(
            name= name,
            mobile= mobile,
            email= email,
            className = className,
            isMale = isMale,
            dateOfBirth = dob
        )
    }

    private fun isMobileInvalid(mobile: String): Boolean {
        @Language("RegExp") val mobilePattern = "[0-9]{10}"
        val mobileMatcher = Pattern.compile(mobilePattern).matcher(mobile)
        return !mobileMatcher.matches()
    }

    private fun isEmailInvalid(email: String): Boolean {
        val emailMatcher = Patterns.EMAIL_ADDRESS.matcher(email)
        return !emailMatcher.matches()
    }

    override fun onBackPressed() {
        showCancelConfirmationDialog()
    }

    /**
     * Shows a dialog to confirm whether the user wants to exit this screen
     */
    private fun showCancelConfirmationDialog() {
        AlertDialog.Builder(this)
            .setMessage(R.string.cancel_confirmation)
            .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
                finish()
            }
            .setNegativeButton(R.string.no) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            .show()
    }

    /**
     * Sets up list of classes and attaches it to the spinner
     */
    private fun setUpClassDropDown() {
        val classNames = arrayOf(
            "M.Sc (C.S)",
            "B.Sc (C.S)",
            "MCA",
            "BCA",
            "B.Com",
            "BBA",
            "M.Com"
        )
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            classNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        class_spinner.adapter = adapter
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        date_field.text = "$dayOfMonth/$month/$year"
    }
}