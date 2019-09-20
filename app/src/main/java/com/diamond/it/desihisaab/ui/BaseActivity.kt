package com.diamond.it.desihisaab.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.diamond.it.desihisaab.common.AppManager
import com.diamond.it.desihisaab.screen.ScreenHelper

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener, ScreenHelper.MessageReceiver{

    protected lateinit var handler : Handler
    protected lateinit var  context : Context
    protected var disableClick = false
    protected lateinit var appManager: AppManager
    protected lateinit var screenHelper: ScreenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        handler = Handler()
        context = getActivityContext()
        appManager = AppManager.getInstance(context)
        screenHelper = appManager.getScreenHelper()
    }

    protected abstract fun getLayoutId():Int
    protected abstract fun getActivityContext():Context
    protected abstract fun initUi()


}
