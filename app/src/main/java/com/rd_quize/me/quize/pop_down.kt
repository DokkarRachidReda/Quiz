package com.rd_quize.me.quize

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class pop_down : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_down)

        var handler= android.os.Handler()
        handler.postDelayed(object:Runnable {
            override fun run() {
                finish()
            }

        },4000)
    }
}
