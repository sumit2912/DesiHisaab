package com.diamond.it.desihisaab.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.common.AlertDialogManager
import com.diamond.it.desihisaab.model.data_model.Data
import com.diamond.it.desihisaab.pref.PrefConst
import com.diamond.it.desihisaab.screen.Screen
import com.diamond.it.desihisaab.screen.ScreenHelper
import com.diamond.it.desihisaab.utils.Utils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity(), AlertDialogManager.AlertDialogListener {

    private val TAG = Screen.SPLASH_ACTIVITY
    private lateinit var intentHisaab: Intent
    private lateinit var objectAnimator: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        Utils.makeFullScreenActivity(this@SplashActivity)
        super.onCreate(savedInstanceState)
        objectAnimator = ObjectAnimator.ofFloat(iv, "rotationY", 0.0f, 360f)
        objectAnimator.duration = 2000
        objectAnimator.repeatCount = ObjectAnimator.INFINITE
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        objectAnimator.start()
        if (TextUtils.isEmpty(prefManager.getString(PrefConst.PREF_HISAAB_TITLE))) {
            prefManager.setBoolean(PrefConst.PREF_DEF_QUANTITY, true)
        }
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
        super.onMessageReceived(from, msg, data)
    }

    override fun getAlertDialogListener(): AlertDialogManager.AlertDialogListener {
        return this@SplashActivity
    }

    override fun onPositiveClicked(type: String) {

    }

    override fun onNegativeClicked(type: String) {

    }

}
