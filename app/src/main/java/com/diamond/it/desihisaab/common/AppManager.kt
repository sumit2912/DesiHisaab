package com.diamond.it.desihisaab.common

import android.content.Context
import android.util.Log
import com.diamond.it.desihisaab.screen.ScreenHelper

class AppManager(context: Context) {

    private var context: Context = context
    private lateinit var screenHelper: ScreenHelper

    companion object {
        @Volatile private var appManager: AppManager? = null

        fun getInstance(context: Context): AppManager {
            if (appManager == null) {
                synchronized(this)
                {
                    appManager = AppManager(context)
                }

            }
            return appManager as AppManager
        }
    }

    fun getAppContext(): Context {
        return this.context
    }

    fun setScreenHelper(screenHelper: ScreenHelper) {
        this.screenHelper = screenHelper
    }

    fun getScreenHelper(): ScreenHelper {
        return this.screenHelper
    }
}