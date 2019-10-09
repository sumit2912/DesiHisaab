package com.diamond.it.desihisaab.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.common.AlertDialogManager
import com.diamond.it.desihisaab.model.data_model.Data
import com.diamond.it.desihisaab.screen.ScreenHelper
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : BaseActivity(), AlertDialogManager.AlertDialogListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_about_us
    }

    override fun getActivityContext(): Context {
        return this@AboutUsActivity
    }

    override fun initUi() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.about_us)
        tvEmail.setOnClickListener(this)
    }

    override fun addMessageReceiver(): ScreenHelper.MessageReceiver {
        return this@AboutUsActivity
    }

    override fun getAlertDialogListener(): AlertDialogManager.AlertDialogListener {
        return this@AboutUsActivity
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tvEmail -> {
                val intentEmail = Intent(Intent.ACTION_SENDTO)
                intentEmail.setData(Uri.parse("mailto:diamond.it.solutions2019@gmail.com"))
                startActivity(Intent.createChooser(intentEmail,"Send Email"))
            }
        }
    }

    override fun onMessageReceived(from: String, msg: String, data: Data?) {
        super.onMessageReceived(from, msg, data)
    }

    override fun onPositiveClicked(type: String) {

    }

    override fun onNegativeClicked(type: String) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
