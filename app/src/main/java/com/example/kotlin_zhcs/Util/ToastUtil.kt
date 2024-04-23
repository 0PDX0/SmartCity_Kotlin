package com.example.kotlin_zhcs.Util

import android.content.Context
import android.widget.Toast

class ToastUtil {

    companion object{
        private val context : Context? = null

        fun show(context: Context, msg : String){
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
    }

}