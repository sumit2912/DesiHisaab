package com.diamond.it.desihisaab.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.model.data_model.Data
import com.diamond.it.desihisaab.screen.Screen
import com.diamond.it.desihisaab.screen.ScreenHelper
import com.diamond.it.desihisaab.utils.Utils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    private val TAG = Screen.SPLASH_ACTIVITY
    private lateinit var intentHisaab: Intent
    private lateinit var objectAnimator: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        objectAnimator = ObjectAnimator.ofFloat(iv, "rotationY", 0.0f, 360f)
        objectAnimator.duration = 2000
        objectAnimator.repeatCount = ObjectAnimator.INFINITE
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        objectAnimator.start()
        initUi()
    }

    override fun initUi() {
        //  MobileAds.initialize(this,getString(R.string.admob_app_id))
        handler.postDelayed({
            objectAnimator.cancel()
            intentHisaab = Intent(context, HisaabActivity::class.java)
            startActivity(intentHisaab)
            finish()
        }, 2000)
    }

    override fun getActivityContext(): Context {
        return this@SplashActivity
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun addMessageReceiver(): ScreenHelper.MessageReceiver {
        return this@SplashActivity
    }

    override fun onClick(view: View?) {
    }

    override fun onMessageReceived(from: String, msg: String, data: Data?) {

    }

}
