package com.example.kotlin_zhcs.ui.dashboard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_zhcs.App
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.databinding.FragmentAllMoreBinding
import com.example.kotlin_zhcs.login.Repository.api
import com.example.kotlin_zhcs.login.Repository.coroutine
import com.example.kotlin_zhcs.ui.home.HomeFragment
import com.example.kotlin_zhcs.ui.home.adapter.MoreAdapter
import kotlinx.coroutines.Dispatchers

class AllMoreFragment : Fragment() {

    //存储全部服务数据
    val arrMore = ArrayList<HomeFragment.HomeMoreEntity>()

    companion object {
        //根据Type来获取不同的服务模块
        var moreTypeId = 0
    }

    lateinit var binding : FragmentAllMoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //获取服务
        val moreTypeResult = when(moreTypeId) {
            0 -> "全部服务"
            1 -> "便民服务"
            2 -> "生活服务"
            3 -> "车主服务"
            4 -> "其他"
            else -> throw Exception("全部服务模块出错！")
        }
        getService(moreTypeResult)

    }

    private fun getService(typeName: String) {
        //开启协程
        coroutine(Dispatchers.Main){
            //清空集合，
            arrMore.clear()
            //获取数据
            val moreResult = api.getMore()

            if (moreResult.code == 200){
                for (i in moreResult.rows!!){

                    if (typeName == "全部服务"){
                        arrMore.add(
                            HomeFragment.HomeMoreEntity(
                                i.sort, App.url + i.imgUrl, 0, i.serviceName.toString()
                            )
                        )
                    }else if (typeName == "便民服务"){
                        if (i.serviceType == "便民服务"){
                            arrMore.add(
                                HomeFragment.HomeMoreEntity(
                                    i.sort, App.url + i.imgUrl, 0, i.serviceName.toString()
                                )
                            )
                        }
                    }else if (typeName == "生活服务"){
                        if (i.serviceType == "生活服务"){
                            arrMore.add(
                                HomeFragment.HomeMoreEntity(
                                    i.sort, App.url + i.imgUrl, 0, i.serviceName.toString()
                                )
                            )
                        }
                    }else if (typeName == "车主服务"){
                        if (i.serviceType == "车主服务"){
                            arrMore.add(
                                HomeFragment.HomeMoreEntity(
                                    i.sort, App.url + i.imgUrl, 0, i.serviceName.toString()
                                )
                            )
                        }
                    }else if (typeName == "其他"){
                        if (i.serviceType == null){
                            arrMore.add(
                                HomeFragment.HomeMoreEntity(
                                    i.sort, App.url + i.imgUrl, 0, i.serviceName.toString()
                                )
                            )
                        }
                    }


//                    if (i.serviceType.toString() == typeName){
//                        if (typeName == "null"){
//                            arrMore.add(
//                                HomeFragment.HomeMoreEntity(
//                                    i.sort, App.url + i.imgUrl, 0, i.serviceName.toString()
//                                )
//                            )
//                        }else {
//                            //添加数据
//                            arrMore.add(
//                                HomeFragment.HomeMoreEntity(
//                                    i.sort, App.url + i.imgUrl, 0, i.serviceName.toString()
//                                )
//                            )
//                        }
//                    }

                }
                //设置列表适配器
                setRecycleAdapter(arrMore)
            }
        }


    }

    /**
     * 列表适配器
     */
    private fun setRecycleAdapter(data : ArrayList<HomeFragment.HomeMoreEntity>) {
        binding.typeRv.apply {
            this.layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)
            this.adapter = MoreAdapter(
                true,
                arrMore.size,
                this@AllMoreFragment,
                data,
                R.layout.re_item_more
            )

            this.adapter?.notifyItemChanged(arrMore.size)
        }
    }


}




















