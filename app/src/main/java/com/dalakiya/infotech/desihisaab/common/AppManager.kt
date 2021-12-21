package com.dalakiya.infotech.desihisaab.common

import android.content.Context
import com.dalakiya.infotech.desihisaab.pref.PrefManager
import com.dalakiya.infotech.desihisaab.screen.ScreenHelper

class AppManager(private var context: Context) {

    private lateinit var screenHelper: ScreenHelper
    private lateinit var prefManager: PrefManager

    companion object {
        @Volatile
        private var appManager: AppManager? = null

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

    fun setPrefManager(prefManager: PrefManager) {
        this.prefManager = prefManager
    }

    fun getPrefManager(): PrefManager {
        return this.prefManager
    }
}