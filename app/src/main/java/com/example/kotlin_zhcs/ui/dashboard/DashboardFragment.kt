package com.example.kotlin_zhcs.ui.dashboard

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_zhcs.App.Companion.hiddenKeyboard
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.databinding.FragmentDashboardBinding
import com.example.kotlin_zhcs.login.Repository.api
import com.example.kotlin_zhcs.login.Repository.coroutine
import com.example.kotlin_zhcs.login.model.MoreModel
import com.example.kotlin_zhcs.ui.dashboard.dialog.DashSearchDialog
import com.example.kotlin_zhcs.ui.home.adapter.MoreAdapter1
import kotlinx.coroutines.Dispatchers


class DashboardFragment : Fragment(), View.OnClickListener {

    val names = listOf<String>(
        "全部服务",
        "便民服务",
        "生活服务",
        "车主服务",
        "其他"
    )

    //服务类别
    var serviceClass = ArrayList<String>()

    //全局ID
    lateinit var binding : FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //初始化左侧导航栏
        initTab()

        //初始化右侧服务列表
        initDashRecyc()

        //初始化搜索功能
        initSearch()
    }

    /**
     * 初始化搜索功能
     */
    private fun initSearch() {
        /*监听一*/
        binding.dashSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener{

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

                /*这样监听搜索必须加上 android:singleLine="true"*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH){    //TODO 这个EditorInfo好像包含很多事件，有时间回来看
                    //关闭输入法
                    hiddenKeyboard(requireActivity())
                    alert(v!!.text.toString(), more)
                }

                return false
            }
        })

//        binding.dashSearch.isFocusable  //isFocusable:是否允许EditText获取焦点
        //焦点监听，是否展示取消
        binding.dashSearch.setOnFocusChangeListener(object : View.OnFocusChangeListener{

            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus){
                    binding.dashSearchCancel.visibility = View.VISIBLE
                }else {
                    binding.dashSearchCancel.visibility = View.GONE
                }
            }

        })

        binding.dashSearchCancel.setOnClickListener {
            binding.dashSearch.setText("")
            binding.dashSearch.clearFocus()     //清除焦点
//            binding.dashSearch.requestFocus()   //获取焦点
            hiddenKeyboard(requireActivity())
        }

        /*监听二(这个监听需要自己去获取输入框输入的文字)*/
//        binding.dashSearch.setOnKeyListener { v, keyCode, event ->
//
//            if (keyCode == KeyEvent.KEYCODE_ENTER){
//                Log.e("pdxSearch","搜索执行了,${v.toString()}")
//            }
//
//            false
//        }
    }

    /**
     * 弹出搜索框
     */
    @RequiresApi(Build.VERSION_CODES.R)
    fun alert(inputText : String, more : MoreModel){

        var dialog : DashSearchDialog = DashSearchDialog(context, inputText, more, this)

        dialog.show()

    }

    /**
     * TODO 关闭输入法
     */
    //open的作用在于 让类和方法可以被继承和重写


    lateinit var more : MoreModel

    /**
     * 初始化右侧服务列表
     */
    private fun initDashRecyc() {

        var dashlistone : ArrayList<MoreModel.RowsBean> = ArrayList()
        var dashlisttwo : ArrayList<MoreModel.RowsBean> = ArrayList()
        var dashlistthree : ArrayList<MoreModel.RowsBean> = ArrayList()
        var dashlistfour : ArrayList<MoreModel.RowsBean> = ArrayList()

        coroutine(Dispatchers.Main){

            more = api.getMore()

            for (row in more.rows!!){
                if (row.serviceType == "便民服务"){
                    dashlistone.add(row)
                }else if (row.serviceType == "生活服务"){
                    dashlisttwo.add(row)
                }else if (row.serviceType == "车主服务"){
                    dashlistthree.add(row)
                }else {
                    dashlistfour.add(row)
                }
            }

            binding.dashRecycOne.apply {
                layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false )
                adapter = MoreAdapter1(dashlistone.size,
                                        this@DashboardFragment,
                                        dashlistone,
                                        R.layout.re_item_more)
            }

            binding.dashRecycTwo.apply {
                layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false )
                adapter = MoreAdapter1(dashlisttwo.size,
                    this@DashboardFragment,
                    dashlisttwo,
                    R.layout.re_item_more)
            }

            binding.dashRecycThree.apply {
                layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false )
                adapter = MoreAdapter1(dashlistthree.size,
                    this@DashboardFragment,
                    dashlistthree,
                    R.layout.re_item_more)
            }

            binding.dashRecycFour.apply {
                layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false )
                adapter = MoreAdapter1(dashlistfour.size,
                    this@DashboardFragment,
                    dashlistfour,
                    R.layout.re_item_more)
            }
        }

    }


    /**
     * 初始化左侧导航栏
     */
    private fun initTab() {

        binding.dashTabOne.setOnClickListener(this)
        binding.dashTabTwo.setOnClickListener(this)
        binding.dashTabThree.setOnClickListener(this)
        binding.dashTabFour.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        if (v != null){
            if (v.id == R.id.dash_tab_one){
                tabBck("1",view)
            }else if (v.id == R.id.dash_tab_two){
                tabBck("2", view)
            }else if (v.id == R.id.dash_tab_three){
                tabBck("3", view)
            }else if (v.id == R.id.dash_tab_four){
                tabBck("4", view)
            }

        }

    }


    fun tabBck(index: String, view: View?){

        for (i in 1 until 5){   // 1 until 4 ：1 2 3

            binding.dashTabOne.background = ColorDrawable(Color.parseColor("#22AAAAAA"))
            binding.dashTabTwo.background = ColorDrawable(Color.parseColor("#22AAAAAA"))
            binding.dashTabThree.background = ColorDrawable(Color.parseColor("#22AAAAAA"))
            binding.dashTabFour.background = ColorDrawable(Color.parseColor("#22AAAAAA"))

        }

        if (index == "1"){

            binding.dashTabOne.background = ColorDrawable(Color.parseColor("#FFFFFF"))
            binding.scroll.scrollToDescendant(binding.scrollOne)
//            binding.scroll.smoothScrollBy(0, binding.scrollOne.scrollY)
        }else if (index == "2"){

            binding.dashTabTwo.background = ColorDrawable(Color.parseColor("#FFFFFF"))
            binding.scroll.scrollToDescendant(binding.scrollTwo)
//            binding.scroll.smoothScrollBy(0, binding.scrollTwo.scrollY)
        }else if (index == "3"){

            binding.dashTabThree.background = ColorDrawable(Color.parseColor("#FFFFFF"))
            binding.scroll.scrollToDescendant(binding.scrollThree)
//            binding.scroll.smoothScrollBy(0, binding.scrollThree.scrollY)
        }else if (index == "4"){

            binding.dashTabFour.background = ColorDrawable(Color.parseColor("#FFFFFF"))
            binding.scroll.scrollToDescendant(binding.scrollFour)
//            binding.scroll.smoothScrollBy(0, binding.scrollFour.scrollY)
        }

    }


}





















