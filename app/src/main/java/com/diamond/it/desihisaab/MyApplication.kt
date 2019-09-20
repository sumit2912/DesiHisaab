package com.diamond.it.desihisaab

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.multidex.MultiDexApplication
import com.diamond.it.desihisaab.common.AppManager
import com.diamond.it.desihisaab.common.Callbacks
import com.diamond.it.desihisaab.pref.PrefConst
import com.diamond.it.desihisaab.pref.PrefManager
import com.diamond.it.desihisaab.screen.ScreenHelper

class MyApplication : MultiDexApplication() {

    private var appManager: AppManager? = null
    private lateinit var screenHelper: ScreenHelper
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var prefManager: PrefManager
    private lateinit var callbacks: Callbacks

    override fun onCreate() {
        super.onCreate()
        callbacks = Callbacks()
        registerActivityLifecycleCallbacks(callbacks)
        initBasic()
    }

    @SuppressLint("CommitPrefEdits")
    private fun initBasic() {
        if(appManager == null)
        appManager = AppManager.getInstance(applicationContext)
        screenHelper = ScreenHelper(appManager!!)
        appManager?.setScreenHelper(screenHelper)
        sharedPreference = applicationContext.getSharedPreferences(PrefConst.PREF_FILE_NAME, Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
        prefManager = PrefManager(appManager!!, sharedPreference, editor)
        appManager?.setPrefManager(prefManager)
    }

    fun getAppManager(): AppManager {
        if(appManager == null){
            appManager = AppManager.getInstance(applicationContext)
        }
        return appManager as AppManager
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(callbacks)
    }

}