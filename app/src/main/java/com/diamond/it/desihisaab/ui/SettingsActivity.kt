package com.diamond.it.desihisaab.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.model.data_model.Data
import com.diamond.it.desihisaab.pref.PrefConst
import com.diamond.it.desihisaab.screen.Screen
import com.diamond.it.desihisaab.screen.ScreenHelper
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_settings
    }

    override fun getActivityContext(): Context {
        return this@SettingsActivity
    }

    override fun initUi() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.settings)

        var titleOfApp = prefManager.getString(PrefConst.PREF_HISAAB_TITLE)
        if(TextUtils.isEmpty(titleOfApp)){
           titleOfApp = getString(R.string.app_name)
        }
        edTitle.setText(titleOfApp)
        btnSave.setOnClickListener(this)
    }

    override fun addMessageReceiver(): ScreenHelper.MessageReceiver {
        return this@SettingsActivity
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnSave -> {
                val myTitle = edTitle.text.toString()
                if(myTitle.isEmpty()){
                    Toast.makeText(context,"Please enter text",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"Saved Successfully...",Toast.LENGTH_LONG).show()
                    prefManager.setString(PrefConst.PREF_HISAAB_TITLE, myTitle)
                    finish()
                }
            }
        }
    }

    override fun onMessageReceived(from: String, msg: String, data: Data?) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        screenHelper.sendMessage(Screen.SETTINGS_ACTIVITY, Screen.HISAAB_ACTIVITY, "Close Drawer", null)
        finish()
    }
}
