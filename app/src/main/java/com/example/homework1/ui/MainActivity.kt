package com.example.homework1.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.homework1.R
import com.example.homework1.databinding.ActivityMainBinding
import com.example.homework1.extensions.onEnterPressed
import com.example.homework1.extensions.showErrorMessage
import com.example.homework1.ui.common.InputState
import data.Student

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState != null) {
            binding.tvOutput.text = savedInstanceState.getString(TV_DATA_OUTPUT_TAG)
            binding.etDataInput.setText(savedInstanceState.getString(ET_DATA_INPUT_TAG))
        }

        with(binding) {
            etDataInput.onEnterPressed {
                when (val result = viewModel.getStudentFromString(etDataInput.text.toString())) {
                    is InputState.Data<*> -> {
                        viewModel.addStudentRecord(result.data as Student)
                        etDataInput.setText(String())
                    }
                    is InputState.Error<*> -> {
                        showValidationError(result.errorType as MainViewModel.ValidationResult)
                    }
                }
            }

            btnShow.setOnClickListener {
                var output = String()
                viewModel.students.entries.forEach {
                    val student = it.value
                    output += "${it.key} ${student.surname} ${student.name} ${student.grade} ${student.birthDateYear} \n"
                }
                tvOutput.text = output
            }
        }

        setContentView(binding.root)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString(ET_DATA_INPUT_TAG, binding.etDataInput.text.toString())
            putString(TV_DATA_OUTPUT_TAG, binding.tvOutput.text.toString())
        }

        super.onSaveInstanceState(outState)
    }

    private fun showValidationError(validationResult: MainViewModel.ValidationResult) {
        when (validationResult) {
            MainViewModel.ValidationResult.NAME -> {
                showErrorMessage(R.string.mainScreen_nameValidationError)
            }
            MainViewModel.ValidationResult.SURNAME -> {
                showErrorMessage(R.string.mainScreen_surnameValidationError)
            }
            MainViewModel.ValidationResult.GRADE -> {
                showErrorMessage(R.string.mainScreen_gradeValidationError)
            }
            MainViewModel.ValidationResult.YEAR -> {
                showErrorMessage(R.string.mainScreen_birthdateYearValidationError)
            }
            MainViewModel.ValidationResult.MISSING_FIELDS -> {
                showErrorMessage(R.string.mainScreen_fieldCountValidationError)
            }
            else -> throw error("Unexpected ValidationResult state")
        }
    }

    companion object {
        const val ET_DATA_INPUT_TAG = "etInputTag"
        const val TV_DATA_OUTPUT_TAG = "tvOutputTag"
    }
}

