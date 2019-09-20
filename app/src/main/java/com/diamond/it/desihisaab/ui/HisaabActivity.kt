package com.diamond.it.desihisaab.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.adapter.DesiHisaabAdapter
import com.diamond.it.desihisaab.common.FinalTotal
import com.diamond.it.desihisaab.model.data_model.Calculation
import com.diamond.it.desihisaab.model.data_model.Data
import com.diamond.it.desihisaab.screen.Screen
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_hisaab.*


class HisaabActivity : BaseActivity(), FinalTotal, NavigationView.OnNavigationItemSelectedListener {

    private val TAG = Screen.HISAAB_ACTIVITY
    private lateinit var llManager: LinearLayoutManager
    private lateinit var desiHisaabAdapter: DesiHisaabAdapter
    private lateinit var finalTotal: FinalTotal
    private lateinit var actionbardrawer: ActionBarDrawerToggle
    private var intentSettings: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    override fun initUi() {
        actionbardrawer = ActionBarDrawerToggle(this, drawerlayout, R.string.Open, R.string.Close)
        drawerlayout.addDrawerListener(actionbardrawer)
        actionbardrawer.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nv.setNavigationItemSelectedListener(this)
        llManager = LinearLayoutManager(context)
        finalTotal = this
        desiHisaabAdapter = DesiHisaabAdapter(context, getDefaultList(), finalTotal)
        rv.layoutManager = llManager
        rv.adapter = desiHisaabAdapter
        /* adView.adUnitId = getString(R.string.add_unit_id)
         adView.adSize = AdSize.BANNER
         val adRequestBuilder = AdRequest.Builder()
         val adRequest = adRequestBuilder.build()
         adView.loadAd(adRequest)*/
    }

    override fun onResume() {
        super.onResume()
        disableClick = false
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

    override fun onFinalTotalChanged(total: String) {
        tvFinalTotal.text = total
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                drawerlayout.closeDrawer(Gravity.LEFT, true)
                if (intentSettings == null) {
                    intentSettings = Intent(context, SettingsActivity::class.java)
                }
                if (!disableClick && intentSettings != null) {
                    disableClick = true
                    startActivity(intentSettings)
                }
            }

            else -> {
                return true
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionbardrawer.onOptionsItemSelected(item)) {
            return true
        }
        if (item.itemId == R.id.plus) {
            var c = Calculation()
            desiHisaabAdapter.getList().add(c)
            desiHisaabAdapter.notifyDataSetChanged()
            rv.scrollToPosition(desiHisaabAdapter.getList().size - 1)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onMessageReceived(from: String, to: String, msg: String, data: Data) {

    }
}


