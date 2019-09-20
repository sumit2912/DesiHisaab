package com.diamond.it.desihisaab

import androidx.multidex.MultiDexApplication
import com.diamond.it.desihisaab.common.AppManager
import com.diamond.it.desihisaab.screen.ScreenHelper

class MyApplication : MultiDexApplication() {

    private lateinit var appManager: AppManager
    private lateinit var screenHelper: ScreenHelper

    override fun onCreate() {
        super.onCreate()
        initBasic()
    }

    private fun initBasic() {
        appManager = AppManager.getInstance(applicationContext)
        screenHelper = ScreenHelper(appManager)
        appManager.setScreenHelper(screenHelper)
    }

    override fun onTerminate() {
        super.onTerminate()

    }

}