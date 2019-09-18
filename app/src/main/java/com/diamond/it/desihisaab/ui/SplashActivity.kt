package com.diamond.it.desihisaab.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.screen.Screen
import com.google.android.gms.ads.MobileAds

class SplashActivity : BaseActivity() {

    private val TAG = Screen.SPLASH_ACTIVITY
    private lateinit var intentHisaab:Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this,getString(R.string.admob_app_id))
        handler.postDelayed(object:Runnable{
            override fun run() {
                intentHisaab = Intent(context,HisaabActivity::class.java)
                startActivity(intentHisaab)
                finish()
            }
        },2000)
        initUi()
    }

    override fun initUi() {
    }

    override fun getActivityContext(): Context {
        return this@SplashActivity
     }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun onClick(view: View?) {
    }

}
