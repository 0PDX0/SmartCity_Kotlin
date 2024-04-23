package com.example.kotlin_zhcs

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager

class App : Application() {

    companion object {
        const val url = "http://124.93.196.45:10001/"

        @SuppressLint("StaticFieldLeak")    //??
        lateinit var context: Context

        //获取token
        val getTokenStr : String
        get() = context.getSharedPreferences("token", Context.MODE_PRIVATE)
            .getString("token","").toString()


        //获取token
        fun getToken() : String = context.getSharedPreferences("token", Context.MODE_PRIVATE)
            .getString("token","").toString()

        //存储token
        fun setToken(token : String) : Unit = context.getSharedPreferences("token", MODE_PRIVATE)
            .edit().putString("token", token).apply()

        //删除Token
        fun delToken() : Unit = context.getSharedPreferences("token", MODE_PRIVATE)
            .edit().remove("token").apply()

        //判断是否第一次(引导页使用)
        fun isStart() : Boolean{
            return context.getSharedPreferences("token", Context.MODE_PRIVATE)
                .getBoolean("true", false)
        }

        // TODO 关闭输入法
        fun hiddenKeyboard(activity : Activity) {
            if (activity != null){                                      //INPUT_METHOD_SERVICE：输入法服务
                val imm: InputMethodManager? = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
                val v: View = activity?.window!!.peekDecorView()
                if (null != v) {
                    imm!!.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }

        /**
         * 动画，
         * 从左往右
         */
        fun getAnimationEndAndStart() : Animation = animationUtil(150f, 0f)

        /**
         * 动画
         * 从下往上
         */
        fun getAnimationBottomAndTop() : Animation = animationUtil(0f, 200f)

        /**
         * 动画
         * 从上往下
         */
        fun getAnimationTopAndBottom(): Animation = animationUtil(0f, -150f)

        /**
         * 从左往右
         */
        fun getAnimationLeftAndEnd(): Animation = animationUtil(150f, 0f)

        /**
         * 从下往上
         */
        fun getAnimationLogin() : Animation = AnimationSet(true).apply {
            /*X轴是横向的，Y轴是竖向的*/
            this.addAnimation(TranslateAnimation(0f, 0f, 600f, 0f).apply {
                this.duration = 600     //持续时间
                this.repeatCount = 0    //动画不会重复
                this.repeatMode = Animation.RESTART //如果动画设置为重复，则每次都会从头开始
            })
        }
        /*此 Kotlin 代码定义了一个函数，该函数返回一个 AnimationSet 对象，该对象是可以一起播放的多个动画的容器。getAnimationLogin()

        在函数内部，使用指定的参数创建一个 TranslateAnimation 对象：

        初始 X 和 Y 坐标设置为0f
        最后的 X 坐标保持不变，但最后的 Y 坐标从 到 移动，从而产生垂直向上的动画。0f600f0f
        然后，使用以下属性自定义 TranslateAnimation 对象：

        this.duration = 600：动画持续时间设置为毫秒。600
        this.repeatCount = 0：动画不会重复。
        this.repeatMode = Animation.RESTART：如果动画设置为重复，则每次都会从头开始。
        最后，使用该方法将 TranslateAnimation 对象添加到 AnimationSet 容器中。然后，该函数返回 AnimationSet。addAnimation()getAnimationLogin()*/


        /**
         * 封装动画
         */
        private fun animationUtil(left : Float, end : Float) : Animation = AnimationSet(true).apply {
            //从左往右的动画
            this.addAnimation(
                TranslateAnimation(left, 0f, end, 0f).apply {
                    this.duration = 700
                    this.repeatCount = 0
                    this.repeatMode = Animation.RESTART
                }
            )

            // 渐变
            this.addAnimation(
                AlphaAnimation(0f, 10f).apply {
                    this.duration = 700
                    this.repeatCount = 0
                    this.repeatMode = Animation.RESTART
                }
            )
        }

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}























