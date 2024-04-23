package com.example.kotlin_zhcs.Util

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.kotlin_zhcs.App
import com.example.kotlin_zhcs.ui.notifications.NotificationsFragment
import com.example.kotlin_zhcs.ui.notifications.activity.LoginActivity

class CheckLoginUtils {

    companion object{

        fun isLogin(context: Context?, autoLogin : Boolean, callback : NotificationsFragment.CheckLogin){

            if (App.getToken().trim() != ""){
                callback.onAlready()
            }else {
                if (autoLogin) {
//                    "用户未登录，即将跳转到登录...".show()
                    ToastUtil.show(context!!, "用户未登录，即将跳转到登录...")

                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        context.startActivity(Intent(context, LoginActivity::class.java))
                    }, 700)
                }
                callback.onNone()
            }

        }

    }

}













