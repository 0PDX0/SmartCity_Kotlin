package com.example.kotlin_zhcs.Util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

//import com.example.kotlin_zhcs.App
import com.example.kotlin_zhcs.App.Companion.context
import com.example.kotlin_zhcs.R


object Util {

    //弹窗
    fun String.show(){
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }

    //网络图片加载
    fun glide(url : String, image : ImageView?) {
        Glide.with(context)
            .load(url)
            .error(R.mipmap.ic_launcher)
            .into(image!!)
    }

    //日志
    fun lg(name : String, msg : String){
        Log.d(name, msg)
    }

    //封装跳转
    fun startActivityUtils(context : Context, activity: Activity){
        context.startActivity(Intent(context, activity::class.java))  //activity.javaClass
    }
}




















