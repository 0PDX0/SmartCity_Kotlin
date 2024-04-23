package com.example.kotlin_zhcs.ui.notifications.Dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import com.example.kotlin_zhcs.App
import com.example.kotlin_zhcs.databinding.OutLoginDialogBinding

class OutLoginDialog(context: Context,var exit: () -> Unit) : Dialog(context) {

    lateinit var binding : OutLoginDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = OutLoginDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.outLogin.setOnClickListener {
            //清除Token
            App.delToken()
//            notificationsFragment.onStart()
            exit()
            dismiss()
        }

        binding.backDesk.setOnClickListener {
            /*Intent.ACTION_MAIN 表示启动一个新的主界面Intent.
            CATEGORY_HOME 表示启动一个主界面的应用.
            Intent.FLAG_ACTIVITY_NEW_TASK 标志表示将新的Activity置于一个新的任务中*/
            var intent : Intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }

        binding.cancel.setOnClickListener {
            dismiss()
        }

        binding.other.setOnClickListener {
            dismiss()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun show() {
        super.show()

        var window : Window? = getWindow()

        if (window != null){
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            var windowManager : WindowManager = window.windowManager

            //过时，不然直接display.width, display.height就能获取到
            var display : Display? = window.context.display

            var attr : WindowManager.LayoutParams = window.attributes

            var windowMetrics : WindowMetrics = windowManager.maximumWindowMetrics  //是获取窗口管理器的最大窗口尺寸信息。这表示当前设备屏幕上能够显示的最大窗口大小。
//            var windowMetrics : WindowMetrics = windowManager.currentWindowMetrics  //是获取当前窗口的尺寸信息。这表示当前正在显示的窗口的大小。

            attr.width = windowMetrics.bounds.width()
//            attr.height = windowMetrics.bounds.height()

            attr.gravity = Gravity.BOTTOM

            window.attributes = attr
        }

    }

}
















