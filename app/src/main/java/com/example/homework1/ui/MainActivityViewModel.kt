package com.example.homework1.ui


import androidx.lifecycle.ViewModel
import com.example.homework1.R
import com.example.homework1.ui.common.InputState
import data.Student
import java.util.*

class MainViewModel : ViewModel() {
    private val _students = mutableMapOf<Long, Student>()
    val students: Map<Long, Student> = _students

    fun getStudentFromString(studentInfo: String): InputState {
        val studentFields = studentInfo.split("\\s+".toRegex())
        val validationResult = isStudentFieldsValid(studentFields)
        return if (validationResult == ValidationResult.ALL_VALID) {
            InputState.Data(
                Student(
                    id = System.currentTimeMillis(),
                    name = studentFields[0],
                    surname = studentFields[1],
                    grade = studentFields[2],
                    birthDateYear = studentFields[3]
                )
            )
        } else {
            InputState.Error(validationResult)
        }
    }

    private fun isStudentFieldsValid(fields: List<String>): ValidationResult {
        return when {
            fields.size != 4 -> ValidationResult.MISSING_FIELDS
            !fields[0].matches("[a-zA-Z]{3,}".toRegex()) -> ValidationResult.NAME
            !fields[1].matches("[a-zA-Z]{3,}".toRegex()) -> ValidationResult.SURNAME
            !validateGrade(fields[2]) -> ValidationResult.GRADE
            !validateYear(fields[3]) -> ValidationResult.YEAR
            else -> ValidationResult.ALL_VALID
        }
    }

    private fun validateYear(yearString: String): Boolean {
        return yearString.matches("[1-2][0-9][0-9][0-9]".toRegex()) &&
                yearString.toInt() in 1930..Calendar.getInstance().get(Calendar.YEAR)
    }

    private fun validateGrade(gradeString: String): Boolean {
        return gradeString.matches("[1]?[0-9]\\D".toRegex()) &&
                gradeString.replace("\\D".toRegex(), "").toInt() in 1..11
    }

    fun addStudentRecord(student: Student) {
        _students.put(
            System.currentTimeMillis(),
            student
        )
    }

    enum class ValidationResult {
        MISSING_FIELDS,
        NAME,
        SURNAME,
        GRADE,
        YEAR,
        ALL_VALID
    }
}