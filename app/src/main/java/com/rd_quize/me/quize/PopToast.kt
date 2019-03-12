package com.rd_quize.me.quize

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.activity_pop_toast.*

class PopToast : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_toast)
        //supportActionBar!!.hide()



        var handler= android.os.Handler()
        handler.postDelayed(object:Runnable {
            override fun run() {
                finish()
            }

        },4000)
    }
}
