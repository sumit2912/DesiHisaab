package com.dalakiya.infotech.desihisaab.common

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.dalakiya.infotech.desihisaab.utils.Utils

class Callbacks : Application.ActivityLifecycleCallbacks{
    
    private lateinit var appManager: AppManager

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        Utils.print(activity.javaClass.simpleName,"Activity Created")
        appManager = AppManager.getInstance(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        Utils.print(activity.javaClass.simpleName,"Activity Started")
    }

    override fun onActivityResumed(activity: Activity) {
        Utils.print(activity.javaClass.simpleName,"Activity Resumed")
    }
    
    override fun onActivityPaused(activity: Activity) {
        Utils.print(activity.javaClass.simpleName,"Activity Paused")
    }

    override fun onActivityStopped(activity: Activity) {
        Utils.print(activity.javaClass.simpleName,"Activity Stopped")
    }
    
    override fun onActivityDestroyed(activity: Activity) {
        Utils.print(activity.javaClass.simpleName,"Activity Destroyed")
        appManager.getScreenHelper().removeActivity(activity.javaClass.simpleName)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
        Utils.print(activity.javaClass.simpleName,"Activity SaveInstanceState")
    }
    
}