package com.dalakiya.infotech.desihisaab.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dalakiya.infotech.desihisaab.common.AlertDialogManager
import com.dalakiya.infotech.desihisaab.common.AppManager
import com.dalakiya.infotech.desihisaab.model.data_model.Data
import com.dalakiya.infotech.desihisaab.pref.PrefManager
import com.dalakiya.infotech.desihisaab.screen.ScreenHelper
import com.dalakiya.infotech.desihisaab.utils.Utils

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener, ScreenHelper.MessageReceiver {

    private val TAG = "BaseActivity"
    protected lateinit var handler: Handler
    protected lateinit var context: Context
    protected var disableClick = false
    protected lateinit var appManager: AppManager
    protected lateinit var screenHelper: ScreenHelper
    protected lateinit var prefManager: PrefManager
    protected lateinit var alertDialogManager: AlertDialogManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        handler = Handler()
        context = getActivityContext()
        appManager = AppManager.getInstance(context)
        screenHelper = appManager.getScreenHelper()
        screenHelper.getDataMessageListenerList()[context.javaClass.simpleName] = addMessageReceiver()
        prefManager = appManager.getPrefManager()
        alertDialogManager = AlertDialogManager(context, getAlertDialogListener())
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun getActivityContext(): Context
    protected abstract fun initUi()
    protected abstract fun addMessageReceiver(): ScreenHelper.MessageReceiver
    protected abstract fun getAlertDialogListener(): AlertDialogManager.AlertDialogListener

    override fun onMessageReceived(from: String, msg: String, data: Data?) {
        val isData = if (data == null) "No" else "YES"
        Utils.print(TAG, "from = $from  Message = $msg Data = $isData")
    }
}
