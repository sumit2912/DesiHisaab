package com.diamond.it.desihisaab.common

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class AlertDialogManager(context: Context,alertDialogListener: AlertDialogListener) {
    private val context = context
    private val alertDialogListener = alertDialogListener
    private lateinit var alertDialog: AlertDialog
    private var alertDialogBuilder: AlertDialog.Builder? = null
    private var title: String? = null
    private var msg: String? = null
    private var type: String? = null
    private var positive: String? = null
    private var negative: String? = null

    fun setTitle(title: String) {
        this.title = title
    }

    fun setMessage(msg: String) {
        this.msg = msg
    }

    fun setType(type: String) {
        this.type = type
    }

    fun setPositive(positive: String) {
        this.positive = positive
    }

    fun setNegative(negative: String) {
        this.negative = negative
    }

    fun show() {
        alertDialogBuilder = AlertDialog.Builder(context)
        if (title != null) {
            alertDialogBuilder?.setTitle(title)
        }
        if (msg != null) {
            alertDialogBuilder?.setMessage(msg)
        }
        if (positive != null) {
            alertDialogBuilder?.setPositiveButton(positive) { dialogInterface: DialogInterface, i: Int ->
                alertDialogListener.onPositiveClicked(type!!)
                dialogInterface.dismiss()
            }
        }
        if (negative != null) {
            alertDialogBuilder?.setNegativeButton(negative) { dialogInterface: DialogInterface, i: Int ->
                alertDialogListener.onNegativeClicked(type!!)
                dialogInterface.dismiss()
            }
        }
        alertDialog = alertDialogBuilder!!.create()
        if(!alertDialog.isShowing){
            alertDialog.show()
        }
    }

    interface AlertDialogListener{
        fun onPositiveClicked(type: String)
        fun onNegativeClicked(type: String)
    }
}