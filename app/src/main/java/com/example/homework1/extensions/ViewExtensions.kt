package com.example.homework1.extensions

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.onEnterPressed(action: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            action.invoke()
        }
        true
    }
}