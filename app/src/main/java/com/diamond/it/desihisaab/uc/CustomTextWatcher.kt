package com.diamond.it.desihisaab.uc

import android.text.Editable
import android.text.TextWatcher

class CustomTextWatcher(myTextWatcher: MyTextWatcher) : TextWatcher {

    private var myTextWatcher: MyTextWatcher

    init {
        this.myTextWatcher = myTextWatcher
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {
        myTextWatcher.onValueChanged(c.toString())
    }

    interface MyTextWatcher {
        fun onValueChanged(string: String)
    }
}