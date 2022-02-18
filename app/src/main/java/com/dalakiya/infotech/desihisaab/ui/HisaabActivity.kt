package com.dalakiya.infotech.desihisaab.ui

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
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dalakiya.infotech.desihisaab.R
import com.dalakiya.infotech.desihisaab.adapter.DesiHisaabAdapter
import com.dalakiya.infotech.desihisaab.common.AlertDialogManager
import com.dalakiya.infotech.desihisaab.common.FinalTotal
import com.dalakiya.infotech.desihisaab.common.FocusListener
import com.dalakiya.infotech.desihisaab.model.data_model.Calculation
import com.dalakiya.infotech.desihisaab.model.data_model.Data
import com.dalakiya.infotech.desihisaab.pref.PrefConst
import com.dalakiya.infotech.desihisaab.screen.Screen
import com.dalakiya.infotech.desihisaab.screen.ScreenHelper
import com.dalakiya.infotech.desihisaab.utils.Utils
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_hisaab.*
import kotlinx.android.synthetic.main.activity_hisaab.clSrNo
import kotlinx.android.synthetic.main.raw_desi_hisaab_adapter_item.*
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
    private var quantityFocused = false
    private var priceFocused = false
    private var quantityPos = -1
    private var pricePos = -1

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
            prefManager.getSrNoVisibility(PrefConst.PREF_SR_NO_VISIBILITY),
            focusListener,
            object : DesiHisaabAdapter.ActionListener {
                override fun onAction(adapterPosition: Int) {
                    /*val c = Calculation()
                    desiHisaabAdapter.getList().add(c)
                    desiHisaabAdapter.notifyItemInserted(desiHisaabAdapter.getList().size - 1)
                    Handler(Looper.getMainLooper()).postDelayed({
                        rv.scrollToPosition(desiHisaabAdapter.getList().size - 1)
                    }, 125)

                    Handler(Looper.getMainLooper()).postDelayed({
                        val editText =
                            rv.findViewHolderForAdapterPosition(adapterPosition + 1)?.itemView?.findViewById<EditText>(
                                R.id.edQuantity
                            )
                        editText?.requestFocus()
                    }, 250)*/
                }
            })
        rv.layoutManager = llManager
        rv.adapter = desiHisaabAdapter
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && llManager.findLastCompletelyVisibleItemPosition() >= desiHisaabAdapter.getList().size - 11) {
                    for (i in 0..9) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val c = Calculation()
                            desiHisaabAdapter.getList().add(c)
                            desiHisaabAdapter.notifyItemInserted(desiHisaabAdapter.getList().size - 1)
                        }, 50)
                    }
                }
            }
        })
        /*ivScrollUp.setOnClickListener {
            rv.scrollToPosition(0)
            Handler(Looper.getMainLooper()).postDelayed({
                val editText =
                    rv.findViewHolderForAdapterPosition(0)?.itemView?.findViewById<EditText>(
                        R.id.edQuantity
                    )
                editText?.post {
                    editText.requestFocus()
                    val imm: InputMethodManager = editText.context
                        .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
                }
            }, 1000)
        }

        ivScrollDown.setOnClickListener {
            var isData = -1
            val end = desiHisaabAdapter.getList().size - 1
            for (index in (0..end).reversed()) {
                Log.e("HA", "index = $index")
                val data = desiHisaabAdapter.getList()[index]
                if (data.total != 0.0) {
                    isData = index
                    break
                }
            }

            if (isData != -1) {
                rv.scrollToPosition(isData)
            } else {
                rv.scrollToPosition(19)
                if (desiHisaabAdapter.getList().size == 20) {
                    for (i in 0..9) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val c = Calculation()
                            desiHisaabAdapter.getList().add(c)
                            desiHisaabAdapter.notifyItemInserted(desiHisaabAdapter.getList().size - 1)
                        }, 50)
                    }
                }
            }

            Handler(Looper.getMainLooper()).postDelayed({
                val editText =
                    rv.findViewHolderForAdapterPosition(
                        if (isData == -1) 19 else
                            isData
                    )?.itemView?.findViewById<EditText>(
                        R.id.edPrice
                    )
                editText?.post {
                    editText.requestFocus()
                    val imm: InputMethodManager = editText.context
                        .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
                }
            }, 1000)
        }*/

        ivPrev.setOnClickListener {
            if (canRun) {
                if (priceFocused && pricePos != -1) {
                    val editText =
                        rv.findViewHolderForAdapterPosition(pricePos)?.itemView?.findViewById<EditText>(
                            R.id.edQuantity
                        )
                    editText?.requestFocus()
                } else if (quantityFocused && quantityPos != -1) {
                    val editText =
                        rv.findViewHolderForAdapterPosition(quantityPos - 1)?.itemView?.findViewById<EditText>(
                            R.id.edPrice
                        )
                    editText?.requestFocus()
                }
            }
        }

        ivNext.setOnClickListener {
            if (canRun) {
                if (quantityFocused && quantityPos != -1) {
                    val editText =
                        rv.findViewHolderForAdapterPosition(quantityPos)?.itemView?.findViewById<EditText>(
                            R.id.edPrice
                        )
                    editText?.requestFocus()
                } else if (priceFocused && pricePos != -1) {
                    val editText =
                        rv.findViewHolderForAdapterPosition(pricePos + 1)?.itemView?.findViewById<EditText>(
                            R.id.edQuantity
                        )
                    editText?.requestFocus()
                    ivNext.isEnabled = false
                    Handler(Looper.getMainLooper()).postDelayed({
                        rv.scrollToPosition(pricePos + 1)
                        ivNext.isEnabled = true
                    }, 100)
                }
            }
        }
        ivMoveDown.setOnClickListener {
            if (quantityPos != -1 || pricePos != -1) {
                if (quantityPos != -1) {
                    pricePos = -1
                    val editText =
                        rv.findViewHolderForAdapterPosition(quantityPos + 1)?.itemView?.findViewById<EditText>(
                            R.id.edQuantity
                        )
                    editText?.requestFocus()
                } else {
                    quantityPos = -1
                    val editText =
                        rv.findViewHolderForAdapterPosition(pricePos + 1)?.itemView?.findViewById<EditText>(
                            R.id.edQuantity
                        )
                    editText?.requestFocus()
                }
            }
        }
        if (!prefManager.getString(PrefConst.PREF_HISAAB)?.isEmpty()!!) {
            if (prefManager.getBoolean(PrefConst.PREF_AUTO_SAVE_ENABLED)) {
                Handler(Looper.getMainLooper()).postDelayed({ restore() }, 1000)
            } else {
                showRestoreAlert()
            }
        }

        if (prefManager.getSrNoVisibility(PrefConst.PREF_SR_NO_VISIBILITY) == View.VISIBLE) {
            clSrNo.visibility = View.VISIBLE
        }
        /* adView.adUnitId = getString(R.string.add_unit_id)
         adView.adSize = AdSize.BANNER
         val adRequestBuilder = AdRequest.Builder()
         val adRequest = adRequestBuilder.build()
         adView.loadAd(adRequest)*/
    }

    var canRun = true
    private val focusListener = object : FocusListener {
        override fun onFocusChange(which: String, pos: Int, hasFocus: Boolean) {
            if (which == "Quantity") {
                quantityFocused = hasFocus
                quantityPos = pos
            }
            if (which == "Price") {
                priceFocused = hasFocus
                pricePos = pos
            }

            if (canRun) {
                canRun = false
                handlerNp.postDelayed(runnableNP, 50)
                if (quantityFocused) {
                    priceFocused = false
                    pricePos = -1
                }
                if (priceFocused) {
                    quantityFocused = false
                    quantityPos = -1
                }
            }
        }
    }

    private val handlerNp = Handler(Looper.getMainLooper())

    private val runnableNP = Runnable {
        if (quantityFocused) {
            priceFocused = false
            quantityFocused = true
        }
        if (priceFocused) {
            quantityFocused = false
            priceFocused = true
        }
        canRun = true
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

    override fun onPause() {
        super.onPause()
        if (prefManager.getBoolean(PrefConst.PREF_AUTO_SAVE_ENABLED)) {
            saveLater()
        }
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

    private fun getDefaultList(): ArrayList<Calculation> {
        val list = ArrayList<Calculation>()
        for (i in 0..19) {
            val c = Calculation()
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionbardrawer.onOptionsItemSelected(item)) {
            return true
        }

        if (item.itemId == R.id.clearAll) {
            clearAllAlert()
            return true
        } else if (item.itemId == R.id.showSr) {
            if (clSrNo.visibility == View.GONE) {
                clSrNo.post { Utils.scaleVisible(context = context, clSrNo) }
                Handler(Looper.getMainLooper()).postDelayed({ item.title = "Hide Sr No" }, 500)
            } else {
                clSrNo.post { Utils.scaleGone(context = context, clSrNo) }
                Handler(Looper.getMainLooper()).postDelayed({ item.title = "Show Sr No" }, 500)
            }
            desiHisaabAdapter.toggleDeleteVisibility()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (prefManager.getSrNoVisibility(PrefConst.PREF_SR_NO_VISIBILITY) == View.VISIBLE) {
            menuInflater.inflate(R.menu.menu_sr_visible, menu)
        } else {
            menuInflater.inflate(R.menu.menu, menu)
        }
        return true
    }

    override fun onMessageReceived(from: String, msg: String, data: Data?) {
        super.onMessageReceived(from, msg, data)
    }

    override fun getAlertDialogListener(): AlertDialogManager.AlertDialogListener {
        return this@HisaabActivity
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onPositiveClicked(type: String) {
        if (type == "Exit Alert") {
            saveLater()
            finish()
        } else if (type == "Restore Alert") {
            restore()
        } else if (type == "Clear All Alert") {
            prefManager.removeValue(PrefConst.PREF_HISAAB)
            desiHisaabAdapter = DesiHisaabAdapter(
                context,
                getDefaultList(),
                finalTotal,
                clSrNo.visibility,
                focusListener,
                object : DesiHisaabAdapter.ActionListener {
                    override fun onAction(adapterPosition: Int) {
                        val c = Calculation()
                        desiHisaabAdapter.getList().add(c)
                        desiHisaabAdapter.notifyItemInserted(desiHisaabAdapter.getList().size - 1)
                        Handler(Looper.getMainLooper()).postDelayed({
                            rv.scrollToPosition(desiHisaabAdapter.getList().size - 1)
                        }, 125)

                        Handler(Looper.getMainLooper()).postDelayed({
                            val editText =
                                rv.findViewHolderForAdapterPosition(adapterPosition + 1)?.itemView?.findViewById<EditText>(
                                    R.id.edQuantity
                                )
                            editText?.requestFocus()
                        }, 250)
                    }
                })
            rv.layoutManager = llManager
            rv.adapter = desiHisaabAdapter
            tvFinalTotal.text = ""
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

    @SuppressLint("NotifyDataSetChanged")
    private fun restore() {
        val list = ArrayList<Calculation>()
        list.addAll(desiHisaabAdapter.getList())
        val jsonArray = JSONArray(prefManager.getString(PrefConst.PREF_HISAAB))
        Utils.print(TAG, "Size = " + jsonArray.length())
        var adapterPosition = -1;
        for (i in 0 until (jsonArray.length())) {
            val calObject = jsonArray.getJSONObject(i)
            val calculation = Calculation()
            calculation.quantity = calObject.optDouble("quantity")
            calculation.price = calObject.optDouble("price")
            calculation.total = calObject.optDouble("total")
            Log.e(TAG, "total = " + calculation.total)
            if (calculation.total > 0) {
                adapterPosition = i
            }
            if (adapterPosition > 19) {
                list.add(calculation)
            } else {
                list[adapterPosition] = calculation
            }
        }
        if (desiHisaabAdapter.getList().size > 19) {
            desiHisaabAdapter.getList().clear()
            desiHisaabAdapter.getList().addAll(list)
        }
        desiHisaabAdapter.notifyDataSetChanged()
        if (prefManager.getBoolean(PrefConst.PREF_AUTO_SCROLL_LAST) && adapterPosition != -1) {
            Handler(Looper.getMainLooper()).postDelayed({
                rv.scrollToPosition(adapterPosition)
            }, 200)
            Handler(Looper.getMainLooper()).postDelayed({
                val editText =
                    rv.findViewHolderForAdapterPosition(adapterPosition)?.itemView?.findViewById<EditText>(
                        R.id.edPrice
                    )
                editText?.requestFocus()
                editText?.setSelection(editText.length())
            }, 500)
        }
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
            if (prefManager.getBoolean(PrefConst.PREF_AUTO_SAVE_ENABLED)) {
                super.onBackPressed()
            } else {
                saveLaterAlert()
            }
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
            if (cal.total > 0) {
                isData = cal.total
                break
            }
        }
        if (isData != 0.0) {
            val jsonArray = JSONArray()
            for (cal in list) {
                if (cal.total > 0) {
                    val jsonObject = JSONObject()
                    jsonObject.put("quantity", cal.quantity)
                    jsonObject.put("price", cal.price)
                    jsonObject.put("total", cal.total)
                    Log.e(TAG, "total = " + cal.total)
                    jsonArray.put(jsonObject)
                }
            }
            prefManager.setString(PrefConst.PREF_HISAAB, jsonArray.toString())
        }
    }

    private fun clearAllAlert() {
        alertDialogManager.setType("Clear All Alert")
        alertDialogManager.setTitle("Alert!")
        alertDialogManager.setMessage("It will clear all saved calculations. Are you sure?")
        alertDialogManager.setPositive("Clear All")
        alertDialogManager.setNegative("Cancel")
        alertDialogManager.show()
    }
}


