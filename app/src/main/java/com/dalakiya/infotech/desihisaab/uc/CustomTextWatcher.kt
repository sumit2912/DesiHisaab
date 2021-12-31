package com.dalakiya.infotech.desihisaab.uc

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.widget.EditText

class CustomTextWatcher(
    private var myTextWatcher: MyTextWatcher) : TextWatcher {
    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(c: CharSequence?, start: Int, before: Int, count: Int) {
        myTextWatcher.onValueChanged(c.toString())
    }

    interface MyTextWatcher {
        fun onValueChanged(string: String)
    }
}