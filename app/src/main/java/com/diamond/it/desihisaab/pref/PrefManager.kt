package com.diamond.it.desihisaab.pref

import android.content.SharedPreferences
import com.diamond.it.desihisaab.common.AppManager

class PrefManager(appManager: AppManager, sharedPreferences: SharedPreferences, editor: SharedPreferences.Editor) {

    private val appManager = appManager
    private val sharedPreferences = sharedPreferences
    private var editor = editor

    fun getAppManager(): AppManager {
        return this.appManager
    }

    fun setString(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun setInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun setLong(key: String, value: Long) {
        editor.putLong(key, value)
        editor.commit()
    }

    fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, -1)
    }

    fun removeValue(key: String) {
        editor.remove(key)
        editor.commit()
    }

    fun clearAll() {
        editor = sharedPreferences.edit()
        editor.clear().apply()
    }
}