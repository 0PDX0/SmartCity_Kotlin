package com.example.kotlin_zhcs.ui.dashboard.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.databinding.DishSearchDialogBinding
import com.example.kotlin_zhcs.login.model.MoreModel
import com.example.kotlin_zhcs.ui.home.adapter.MoreAdapter1

class DashSearchDialog(context: Context?, var str : String, var more : MoreModel, var fr : Fragment) : Dialog(context!!) {

    lateinit var binding : DishSearchDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DishSearchDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var list : ArrayList<MoreModel.RowsBean> = ArrayList()

        for (row in more.rows!!) {
            if (row.serviceName.toString().contains(str)){
                list.add(row)
            }
        }

        binding.dishSearchRecyc.apply {
            //这里用水平线性会一个服务就将整个布局占满了，因为布局文件设置的是Match_parent,而且不能改，改了后全部服务的布局会有部分错位的
//            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
            layoutManager = GridLayoutManager(context, 5, GridLayoutManager.VERTICAL,false)
            adapter = MoreAdapter1(
                list.size,
                fr,
                list.toSet().toMutableList(),
                R.layout.re_item_more
            )
        }

        binding.cancel.setOnClickListener {
            dismiss()
        }
    }


    @RequiresApi(Build.VERSION_CODES.R)
    override fun show() {
        super.show()

        var window : Window? = window

        if (window != null){
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            var windowManager : WindowManager = window.windowManager

            var attr : WindowManager.LayoutParams = window.attributes

            var windowMetrics : WindowMetrics = windowManager.maximumWindowMetrics

            attr.width = windowMetrics.bounds.width()

            attr.gravity = Gravity.BOTTOM

            window.attributes = attr
        }
    }

}




















