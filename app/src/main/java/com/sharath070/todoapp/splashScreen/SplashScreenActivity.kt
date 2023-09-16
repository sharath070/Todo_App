package com.sharath070.todoapp.splashScreen


import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.sharath070.todoapp.MainActivity
import com.sharath070.todoapp.R

class SplashScreenActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen2)


        window.statusBarColor = Color.WHITE

        val windowInsetsController = window.insetsController
        windowInsetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this,
                androidx.appcompat.R.anim.abc_fade_in,
                com.google.android.material.R.anim.abc_fade_out
            )
            startActivity(intent, options.toBundle())
            finish()
        }, 1300)


    }

}