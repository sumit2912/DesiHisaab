package com.dalakiya.infotech.desihisaab.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.dalakiya.infotech.desihisaab.R

object Utils {

    fun print(tag: String, msg: String) {
        System.out.println("$tag :: $msg")
    }

    fun makeFullScreenActivity(activity: AppCompatActivity) {
        val window = activity.window
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun hideKeyBoardFromView(context: Context) {
        val inputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        // Find the currently focused view, so we can grab the correct window
        // token from it.
        var view = (context as Activity).currentFocus
        // If no view currently has focus, create a new one, just so we can grab
        // a window token from it
        if (view == null) {
            view = View(context)
        }
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun scaleVisible(context: Context, view: View) {
        view.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(context, R.anim.scale_visible)
        view.startAnimation(animation)
    }

    fun scaleGone(context: Context, view: View) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.scale_gone)
        view.startAnimation(animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                view.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
    }
}