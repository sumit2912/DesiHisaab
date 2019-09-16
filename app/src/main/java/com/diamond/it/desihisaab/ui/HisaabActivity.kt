package com.diamond.it.desihisaab.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.adapter.DesiHisaabAdapter
import com.diamond.it.desihisaab.model.Calculation
import kotlinx.android.synthetic.main.activity_hisaab.*


class HisaabActivity : BaseActivity() {

    private lateinit var llManager: LinearLayoutManager
    private lateinit var desiHisaabAdapter: DesiHisaabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    override fun initUi() {
        llManager = LinearLayoutManager(context)
        desiHisaabAdapter = DesiHisaabAdapter(context, getDefaultList())
        rv.layoutManager = llManager
        rv.adapter = desiHisaabAdapter
    }

    override fun getActivityContext(): Context {
        return this@HisaabActivity
    }

    override fun onClick(view: View?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_hisaab
    }

    fun getDefaultList(): ArrayList<Calculation> {
        val list = ArrayList<Calculation>()
        for (i in 0..14) {
            var c = Calculation()
            list.add(c)
        }
        return list
    }


}