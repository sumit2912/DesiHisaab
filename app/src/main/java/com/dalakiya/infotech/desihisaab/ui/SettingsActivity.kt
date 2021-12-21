package com.dalakiya.infotech.desihisaab.ui

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.dalakiya.infotech.desihisaab.R
import com.dalakiya.infotech.desihisaab.common.AlertDialogManager
import com.dalakiya.infotech.desihisaab.model.data_model.Data
import com.dalakiya.infotech.desihisaab.pref.PrefConst
import com.dalakiya.infotech.desihisaab.screen.ScreenHelper
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity(), AlertDialogManager.AlertDialogListener {

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
        if (TextUtils.isEmpty(titleOfApp)) {
            titleOfApp = getString(R.string.app_name)
        }
        edTitle.setText(titleOfApp)
        btnSave.setOnClickListener(this)
        switchDefQuantity.isChecked = prefManager.getBoolean(PrefConst.PREF_DEF_QUANTITY)
        switchDefQuantity.setOnCheckedChangeListener { buttonView, isChecked ->
            prefManager.setBoolean(PrefConst.PREF_DEF_QUANTITY, isChecked)
        }
        switchAutoSave.isChecked = prefManager.getBoolean(PrefConst.PREF_AUTO_SAVE_ENABLED)
        switchAutoSave.setOnCheckedChangeListener { buttonView, isChecked ->
            prefManager.setBoolean(PrefConst.PREF_AUTO_SAVE_ENABLED, isChecked)
        }
        switchAutoScroll.isChecked = prefManager.getBoolean(PrefConst.PREF_AUTO_SCROLL_LAST)
        switchAutoScroll.setOnCheckedChangeListener { buttonView, isChecked ->
            prefManager.setBoolean(PrefConst.PREF_AUTO_SCROLL_LAST, isChecked)
        }
        switchSrNo.isChecked =
            prefManager.getSrNoVisibility(PrefConst.PREF_SR_NO_VISIBILITY) == View.VISIBLE
        switchSrNo.setOnCheckedChangeListener { buttonView, isChecked ->
            prefManager.setSrNoVisibility(
                PrefConst.PREF_SR_NO_VISIBILITY,
                if (isChecked) View.VISIBLE else View.GONE
            )
        }
    }

    override fun addMessageReceiver(): ScreenHelper.MessageReceiver {
        return this@SettingsActivity
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnSave -> {
                val myTitle = edTitle.text.toString()
                if (myTitle.isEmpty()) {
                    Toast.makeText(context, "Please enter text", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Saved Successfully...", Toast.LENGTH_LONG).show()
                    prefManager.setString(PrefConst.PREF_HISAAB_TITLE, myTitle)
                    finish()
                }
            }
        }
    }

    override fun onMessageReceived(from: String, msg: String, data: Data?) {
        super.onMessageReceived(from, msg, data)

    }

    override fun getAlertDialogListener(): AlertDialogManager.AlertDialogListener {
        return this@SettingsActivity
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onPositiveClicked(type: String) {

    }

    override fun onNegativeClicked(type: String) {

    }
}
