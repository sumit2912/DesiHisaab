package com.diamond.it.desihisaab.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener{

    protected var handler : Handler ? = null
    protected var  context : Context ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        handler = Handler()
        context = getActivityContext()
    }

    protected abstract fun getLayoutId():Int
    protected abstract fun getActivityContext():Context
    protected abstract fun initUi()


}
