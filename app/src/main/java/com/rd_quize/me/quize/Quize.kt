package com.rd_quize.me.quize

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_quize.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

class Quize : AppCompatActivity() {
   var bList:ArrayList<Button>?= null
    var quizes:ArrayList<Qobj>?= null
    var favShared: SharedPreferences.Editor?=null
    var mShared: SharedPreferences.Editor?=null
    var read: SharedPreferences?=null
    var mony: SharedPreferences?=null
    var current=0
    var idb=-1
    var coin=0
    var areAllTrue:ArrayList<Boolean>?=null
    private lateinit var mInterstitialAd: InterstitialAd
    var winShow=0
    var show=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quize)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)


        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-2333826661674903/8094403591"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mShared=getSharedPreferences("money", Context.MODE_PRIVATE).edit()
        favShared=getSharedPreferences("me", Context.MODE_PRIVATE).edit()
        read=getSharedPreferences("me", Context.MODE_PRIVATE)
        mony=getSharedPreferences("money", Context.MODE_PRIVATE)
        try{
            areAllTrue=ArrayList<Boolean>()
            bList= ArrayList<Button>()
            quizes=ArrayList<Qobj>()
           current=read!!.getInt("me",0)
            coin=mony!!.getInt("money",10)
            readData()

        }catch (e:Exception){ Log.e("eeeee",e.toString())}
        initGame()
        //
    }
    fun initButtons(){
        bList!!.clear()
        bList!!.add(button4);bList!!.add(button5);bList!!.add(button6);bList!!.add(button7)
        bList!!.add(button8);bList!!.add(button9);bList!!.add(button10);bList!!.add(button11)
        bList!!.add(button12); bList!!.add(button13); bList!!.add(button14); bList!!.add(button15)

    }
    fun initLetters(){
        var letters="azertyuiopmlkjhgfdsqwxcvbn"
        var q=quizes!![current].answer
        for (j in 0..q.length-1){
            var r=Random()
            var a=r.nextInt(bList!!.size)
            bList!![a].setText(q[j].toString())
            bList!!.removeAt(a)
        }
        for (i in 0..bList!!.size-1){
            var r=Random()
            var a=r.nextInt(25)

            bList!![i].setText(letters[a].toString())
        }

    }

    fun initGame(){
        nb.setText("   "+coin.toString())
        try {
            qq.setText(quizes!![current].quest)
            initButtons()
            initLetters()
            btn_ans.removeAllViews()
            idb=-1
        }catch (e:Exception){ Log.e("iii",e.toString())}


    }
    fun slct(view:View){
        initButtons()
        when(view.id){
            R.id.button4->{btnSelected(0)}
            R.id.button5->{btnSelected(1)}
            R.id.button6->{btnSelected(2)}
            R.id.button7->{btnSelected(3)}
            R.id.button8->{btnSelected(4)}
            R.id.button9->{btnSelected(5)}
            R.id.button10->{btnSelected(6)}
            R.id.button11->{btnSelected(7)}
            R.id.button12->{btnSelected(8)}
            R.id.button13->{btnSelected(9)}
            R.id.button14->{btnSelected(10)}
            R.id.button15->{btnSelected(11)}
        }
        if(show==15) {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            }
            show=0;mInterstitialAd.loadAd(AdRequest.Builder().build())
        }else{show++}
    }
    fun btnSelected(i:Int){

        try{

            if (bList!![i].text.toString()==""){
                var a=ans.text.toString()
                bList!![i].setText(a[a.length-1].toString())

                ans.setText(a.subSequence(0,a.length-1))
                btn_ans.removeViewAt(idb--);areAllTrue!!.removeAt(areAllTrue!!.size-1)
            }else{
                if (idb<quizes!![current].answer.length-1){
                var a=ans.text.toString()
                var b=bList!![i].text.toString()
                ans.setText(a+b)
                bList!![i].setText("")

                    var btn=Button(this@Quize)
                    btn.layoutParams= ViewGroup.LayoutParams(seBtnWidth(), seBtnWidth()+10)
                    btn.setTextColor(resources.getColor(R.color.colorAccent))
                    btn.textAlignment=View.TEXT_ALIGNMENT_CENTER
                    if (quizes!![current].answer.contains(b,true)){
                        btn.setBackgroundResource(R.drawable.alert_backg);areAllTrue!!.add(true)
                    }else{
                        btn.setBackgroundResource(R.drawable.backgray);areAllTrue!!.add(false)
                    }

                    btn.setPadding(10,0,0,0)
                    btn.id=idb;idb++
                    btn.setText(b)
                    btn_ans.addView(btn)
                    /*if (idb==quizes!![current].answer.length-1){

                        checkAllTrue()
                    }*/
                }
            }
        }catch (e:Exception){ Log.e("rrr",e.toString())}
       checkWin()
    }
    fun checkWin(){
        var a=ans.text.toString()
        if (a.length==quizes!![current].answer.length){
            if (a==quizes!![current].answer){
                if (current != quizes!!.size-1){
                    current++
                    var i=Intent(this@Quize,PopToast::class.java)
                    startActivity(i)
                    favShared!!.putInt("me",current);favShared!!.apply()
                    coin+=5;mShared!!.putInt("money",coin);mShared!!.apply()
                    initGame()
                    ans.setText("")
                    if(winShow==2) {
                        if (mInterstitialAd.isLoaded) {
                            mInterstitialAd.show()
                        }
                        winShow=0;mInterstitialAd.loadAd(AdRequest.Builder().build())
                    }else{winShow++}
                }else{}


            }else{checkAllTrue()}
        }

    }
    fun seBtnWidth():Int{
        var screenWidh=resources.configuration.screenWidthDp
        var w=screenWidh/(quizes!![current].answer.length)
        return w+40
    }
    fun readData(){

        try {
           var reader =  BufferedReader(
                     InputStreamReader(getAssets().open("q.txt")))

            while ( (reader.readLine()) != null) {

                if (reader.readLine().length<70){
                    var a=reader.readLine()
                     var t=a.indexOf('*')
                     var r=a.substring(0,t)
                     var rr=a.substring(t+1,a.length)
                    quizes!!.add(Qobj(r,rr))

                }

            }

                try {
                    reader.close();
                } catch (e:IOException) {
                    //log the
                    Log.e("eedf", "onCreate: "+e.toString(), e);
                }
        } catch ( e:IOException) {
            //log the exception
            Log.e("rttte", "onCreate: "+e.toString(), e);
        }
    }
fun checkAllTrue(){
    var e=true
    for(a in areAllTrue!!){
        if (!a){
            e=false
        }
    }

    if (e){

        if (coin>=1){
            coin=coin-1;nb.setText("   "+coin.toString())
            mShared!!.putInt("money",coin);mShared!!.apply()
            var i=Intent(this@Quize,pop_down::class.java)
            startActivity(i)
        }
        }
}
    fun helpNext(view: View){
        if (current<=quizes!!.size){
            if (coin>=3){
                current++
                favShared!!.putInt("me",current);favShared!!.apply()
                coin-=3;mShared!!.putInt("money",coin);mShared!!.apply()
                initGame()
                ans.setText("")
            }
        }


    }
    fun helpBack(view: View){
        if (current>=1){
            current--
            favShared!!.putInt("me",current);favShared!!.apply()
            initGame()
            ans.setText("")
        }

    }
}
