package com.diamond.it.desihisaab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diamond.it.desihisaab.screen.Screen

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        System.out.println(Screen.SPLASH_ACTIVITY)
    }
}
