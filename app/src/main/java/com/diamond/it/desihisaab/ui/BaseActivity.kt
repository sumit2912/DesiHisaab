package com.diamond.it.desihisaab.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
    }

    protected abstract fun getLayoutId():Int
}
