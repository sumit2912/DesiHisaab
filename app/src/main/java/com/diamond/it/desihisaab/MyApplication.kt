package com.diamond.it.desihisaab

import androidx.multidex.MultiDexApplication
import com.diamond.it.desihisaab.common.AppManager

class MyApplication : MultiDexApplication() {

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