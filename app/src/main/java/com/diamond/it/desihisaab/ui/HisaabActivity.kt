package com.diamond.it.desihisaab.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.diamond.it.desihisaab.R
import com.diamond.it.desihisaab.adapter.DesiHisaabAdapter
import com.diamond.it.desihisaab.common.AlertDialogManager
import com.diamond.it.desihisaab.common.FinalTotal
import com.diamond.it.desihisaab.model.data_model.Calculation
import com.diamond.it.desihisaab.model.data_model.Data
import com.diamond.it.desihisaab.pref.PrefConst
import com.diamond.it.desihisaab.screen.Screen
import com.diamond.it.desihisaab.screen.ScreenHelper
import com.diamond.it.desihisaab.utils.Utils
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_hisaab.*
import org.json.JSONArray
import org.json.JSONObject


class HisaabActivity : BaseActivity(), FinalTotal, NavigationView.OnNavigationItemSelectedListener,
    AlertDialogManager.AlertDialogListener {

    val TAG = Screen.HISAAB_ACTIVITY
    private val REQ_CALL = 101
    private lateinit var llManager: LinearLayoutManager
    private lateinit var desiHisaabAdapter: DesiHisaabAdapter
    private lateinit var finalTotal: FinalTotal
    private lateinit var actionbardrawer: ActionBarDrawerToggle
    private var intentSettings: Intent? = null
    private var intentLocation: Intent? = null
    private var intentAboutUs: Intent? = null

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
        desiHisaabAdapter = DesiHisaabAdapter(
            context,
            getDefaultList(),
            finalTotal,
            object : DesiHisaabAdapter.ActionListener {
                override fun onAction(action: String) {
                    if (action.equals("scrollTop")) {
                        Utils.hideKeyBoardFromView(context)
                        Handler(Looper.getMainLooper()).postDelayed({
                            llManager.scrollToPosition(0)
                            val editText: EditText? =
                                rv.findViewHolderForAdapterPosition(0)?.itemView?.findViewById(R.id.edQuantity)
                            editText?.requestFocus()
                        }, 1000)
                    }
                }
            })
        rv.layoutManager = llManager
        rv.adapter = desiHisaabAdapter
        if (!prefManager.getString(PrefConst.PREF_HISAAB)?.isEmpty()!!) {
            showRestoreAlert()
        }
        /* adView.adUnitId = getString(R.string.add_unit_id)
         adView.adSize = AdSize.BANNER
         val adRequestBuilder = AdRequest.Builder()
         val adRequest = adRequestBuilder.build()
         adView.loadAd(adRequest)*/
    }

    private fun showRestoreAlert() {
        alertDialogManager.setType("Restore Alert")
        alertDialogManager.setTitle("Restore Alert!")
        alertDialogManager.setMessage("Restore previous calculation if any stored?")
        alertDialogManager.setPositive("Restore")
        alertDialogManager.setNegative("Cancel")
        alertDialogManager.show()
    }

    override fun onResume() {
        super.onResume()
        val titleOfApp = prefManager.getString(PrefConst.PREF_HISAAB_TITLE)
        supportActionBar?.title =
            if (TextUtils.isEmpty(titleOfApp)) getString(R.string.app_name) else titleOfApp
        disableClick = false
    }

    override fun getActivityContext(): Context {
        return this@HisaabActivity
    }

    override fun addMessageReceiver(): ScreenHelper.MessageReceiver {
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
                Utils.hideKeyBoardFromView(context)
                drawerlayout.closeDrawer(GravityCompat.START, true)
                if (intentSettings == null) {
                    intentSettings = Intent(context, SettingsActivity::class.java)
                }
                if (!disableClick && intentSettings != null) {
                    disableClick = true
                    startActivity(intentSettings)
                }
            }

            /*R.id.contactUs -> {
                Utils.hideKeyBoardFromView(context)
                drawerlayout.closeDrawer(GravityCompat.START, true)
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkCallPermission()) {
                        contactUs()
                    } else {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQ_CALL)
                    }
                } else {
                    contactUs()
                }
            }

            R.id.locateUs -> {
                Utils.hideKeyBoardFromView(context)
                drawerlayout.closeDrawer(GravityCompat.START, false)
                //val uri = String.format(Locale.ENGLISH,"geo:%f,%f",21.640639,69.609194)
                intentLocation = Intent(context, LocateUsActivity::class.java)
                if(!disableClick && intentLocation != null) {
                    disableClick = true
                    startActivity(intentLocation)
                }
            }*/

            R.id.aboutUs -> {
                Utils.hideKeyBoardFromView(context)
                drawerlayout.closeDrawer(GravityCompat.START, false)
                intentAboutUs = Intent(context, AboutUsActivity::class.java)
                if (!disableClick && intentAboutUs != null) {
                    disableClick = true
                    startActivity(intentAboutUs)
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
            val c = Calculation()
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

    override fun onMessageReceived(from: String, msg: String, data: Data?) {
        super.onMessageReceived(from, msg, data)
    }

    override fun getAlertDialogListener(): AlertDialogManager.AlertDialogListener {
        return this@HisaabActivity
    }

    override fun onPositiveClicked(type: String) {
        if (type == "Exit Alert") {
            saveLater()
            finish()
        } else if (type == "Restore Alert") {
            restore()
        }
    }

    override fun onNegativeClicked(type: String) {
        if (type == "Exit Alert") {
            prefManager.removeValue(PrefConst.PREF_HISAAB)
            finish()
        } else if (type == "Restore Alert") {
            prefManager.removeValue(PrefConst.PREF_HISAAB)
        }
    }

    private fun restore() {
        val list = ArrayList<Calculation>()
        val jsonArray = JSONArray(prefManager.getString(PrefConst.PREF_HISAAB))
        Utils.print(TAG, "Size = " + jsonArray.length())
        for (i in 0 until (jsonArray.length() - 1)) {
            val calObject = jsonArray.getJSONObject(i)
            val calculation = Calculation()
            calculation.quantity = calObject.optDouble("quantity")
            calculation.price = calObject.optDouble("price")
            calculation.total = calObject.optDouble("total")
            list.add(calculation)
        }
        desiHisaabAdapter.getList().clear()
        desiHisaabAdapter.getList().addAll(list)
        desiHisaabAdapter.notifyDataSetChanged()
    }

    @SuppressLint("MissingPermission")
    private fun contactUs() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:9033073049")
        startActivity(callIntent)
    }

    fun checkCallPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_CALL && grantResults.size > 0 && checkCallPermission()) {
            contactUs()
        } else {
            Toast.makeText(context, "Grant call permission to call us.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START)
        } else {
            saveLaterAlert()
        }
    }

    private fun saveLaterAlert() {
        alertDialogManager.setType("Exit Alert")
        alertDialogManager.setTitle("Exit Alert!")
        alertDialogManager.setMessage("Save for later calculation and exit?")
        alertDialogManager.setPositive("Save and Exit")
        alertDialogManager.setNegative("Exit")
        alertDialogManager.show()
    }

    private fun saveLater() {
        val list = desiHisaabAdapter.getList()
        prefManager.removeValue(PrefConst.PREF_HISAAB)
        var isData = 0.0
        for (cal in list) {
            if (cal.quantity != 0.0 || cal.price != 0.0) {
                isData = if (cal.quantity == 0.0) cal.price else cal.quantity
            }
        }
        if (isData != 0.0) {
            val jsonArray = JSONArray()
            for (cal in list) {
                val jsonObject = JSONObject()
                jsonObject.put("quantity", cal.quantity)
                jsonObject.put("price", cal.price)
                jsonObject.put("total", cal.total)
                jsonArray.put(jsonObject)
            }
            prefManager.setString(PrefConst.PREF_HISAAB, jsonArray.toString())
        }
    }
}


