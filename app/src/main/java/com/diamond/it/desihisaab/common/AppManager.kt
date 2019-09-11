package com.diamond.it.desihisaab.common

import android.util.Log

class AppManager {

    companion object{
        @Volatile private var appManager:AppManager ? = null

        fun getInstance():AppManager
        {
            if (appManager==null)
            {
                synchronized(this)
                {
                    appManager = AppManager()
                    Log.v("AppManager","App Manager Object is created.")
                }

            }
            return appManager as AppManager
        }
    }
}