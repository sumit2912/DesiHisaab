package com.diamond.it.desihisaab.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import com.diamond.it.desihisaab.R


class HisaabActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    override fun initUi() {
    }

    override fun getActivityContext(): Context {
        return this@HisaabActivity
     }

    override fun onClick(view: View?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_hisaab
    }

}