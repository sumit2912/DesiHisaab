package com.diamond.it.desihisaab.screen

import androidx.appcompat.app.AppCompatActivity
import com.diamond.it.desihisaab.common.AppManager
import com.diamond.it.desihisaab.model.data_model.Data

class ScreenHelper(appManager: AppManager) {

    private var appManager: AppManager = appManager
    private val activityList: HashMap<String,AppCompatActivity> = HashMap()

    fun getActivityList():HashMap<String,AppCompatActivity>{
        return this.activityList
    }

    interface MessageReceiver {
        fun onMessageReceived(from: String, to: String, msg: String, data: Data)
    }
}