package com.rd_quize.me.quize

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // supportActionBar!!.hide()
        MobileAds.initialize(this, "ca-app-pub-2333826661674903~1015450422")

    }


    fun quize(view:View){
        startActivity(Intent(this@MainActivity,Quize::class.java))
    }
    fun coming(view:View){
       Toast.makeText(this@MainActivity,"coming soon",Toast.LENGTH_LONG).show()
    }
    fun share(view: View){
        var m= Intent(Intent.ACTION_SEND)
        m.setType("text/plain")
        m.putExtra(Intent.EXTRA_SUBJECT,"share this app")
        m.putExtra(Intent.EXTRA_TEXT,"share...")
        startActivity(Intent.createChooser(m,"Share"))
    }
}
