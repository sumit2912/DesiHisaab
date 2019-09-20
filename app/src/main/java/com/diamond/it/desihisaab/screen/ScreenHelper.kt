package com.diamond.it.desihisaab.screen

import androidx.appcompat.app.AppCompatActivity
import com.diamond.it.desihisaab.common.AppManager
import com.diamond.it.desihisaab.model.data_model.Data
import com.diamond.it.desihisaab.utils.Utils

class ScreenHelper(appManager: AppManager) {

    private var appManager: AppManager = appManager
    private val activityList: HashMap<String, AppCompatActivity> = HashMap()
    private val dataMessageListenerList: HashMap<String, MessageReceiver> = HashMap()

    fun getAppManager(): AppManager {
        return this.appManager
    }

    fun getActivityList(): HashMap<String, AppCompatActivity> {
        return this.activityList
    }

    fun getDataMessageListenerList(): HashMap<String, MessageReceiver> {
        return this.dataMessageListenerList
    }

    fun sendMessage(fromScreen: String, toScreen: String, msg: String, data: Data?) {
        Utils.print("ScreenHelper", "fromScreen = $fromScreen  toScreen = $toScreen  msg = $msg")
        dataMessageListenerList[toScreen]?.onMessageReceived(fromScreen, msg, data)
    }

    fun removeActivity(screen: String) {
        activityList.remove(screen)
        dataMessageListenerList.remove(screen)
    }

    interface MessageReceiver {
        fun onMessageReceived(from: String, msg: String, data: Data?)
    }
}