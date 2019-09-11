package com.diamond.it.desihisaab

import android.app.Application
import com.diamond.it.desihisaab.common.AppManager

class MyApplication : Application() {

    private var appManager: AppManager? = null

    override fun onCreate() {
        super.onCreate()
        appManager = AppManager.getInstance()
    }

    override fun onTerminate() {
        super.onTerminate()
        appManager = null
    }

}