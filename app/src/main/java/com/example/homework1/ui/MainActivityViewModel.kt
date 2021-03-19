package com.example.homework1.ui

import androidx.lifecycle.ViewModel
import data.Student
import java.lang.IllegalArgumentException
import java.util.*

class MainViewModel : ViewModel() {
    val students = mutableMapOf<Long, Student>()

    @Throws(IllegalArgumentException::class)
    fun getStudentFromString(studentInfo: String): Student {
        val studentFields = studentInfo.split("\\s+".toRegex())
        if (studentFields.size == 4 && isStudentFieldsValid(studentFields)) {
            return Student(
                id = System.currentTimeMillis(),
                name = studentFields[0],
                surname = studentFields[1],
                grade = studentFields[2],
                birthDateYear = studentFields[3]
            )
        } else {
            throw IllegalArgumentException()
        }
    }

    private fun isStudentFieldsValid(fields: List<String>): Boolean {
        return fields[0].matches("[a-zA-Z]{3,}".toRegex()) && //Name
                fields[1].matches("[a-zA-Z]{3,}".toRegex()) && //Surname
                validateGrade(fields[2]) && //Grade
                validateYear(fields[3]) // Year
    }

    private fun validateYear(yearString: String): Boolean {
        return yearString.matches("[1-2][0-9][0-9][0-9]".toRegex()) &&
                yearString.toInt() in 1930..Calendar.getInstance().get(Calendar.YEAR)
    }

    private fun validateGrade(gradeString: String): Boolean {
        return gradeString.matches("[1]?[0-9]\\D".toRegex()) &&
                gradeString.replace("\\D".toRegex(), "").toInt() in 1..11
    }
}