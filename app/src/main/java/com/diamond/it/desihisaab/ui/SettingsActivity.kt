package com.diamond.it.desihisaab.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.model.data_model.Data

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    override fun getLayoutId(): Int {
     return R.layout.activity_settings
    }

    override fun getActivityContext(): Context {
        return SettingsActivity@this
    }

    override fun initUi() {

    }

    override fun onClick(view: View?) {

    }

    override fun onMessageReceived(from: String, to: String, msg: String, data: Data) {

    }

}
