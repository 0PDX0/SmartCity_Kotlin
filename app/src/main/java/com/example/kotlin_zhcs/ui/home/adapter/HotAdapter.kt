package com.example.kotlin_zhcs.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kotlin_zhcs.App
import com.example.kotlin_zhcs.App.Companion.url
import com.example.kotlin_zhcs.Base.BaseRecyclerAdapter
import com.example.kotlin_zhcs.Util.Util.glide
import com.example.kotlin_zhcs.databinding.ReItemHotBinding
import com.example.kotlin_zhcs.login.model.NewsModel
import com.example.kotlin_zhcs.ui.ShowNewsActivity

class HotAdapter(
    private val list : MutableList<NewsModel.RowsBean>,
    private val context : Context?,
    private val layoutId : Int
) : BaseRecyclerAdapter<NewsModel.RowsBean>(list, layoutId) {

    lateinit var binding : ReItemHotBinding

    override fun setData(
        view: BaseViewHolder.ViewFind,
        data: NewsModel.RowsBean,
        position: Int,
        holder: BaseViewHolder
    ) {

        //添加动画
        holder.itemView.animation = App.getAnimationEndAndStart()

        //依次获取每条数据信息
        val d = data

        /*这里用let是因为这里的view是可能为空，但是那边glide需要的view是不能为空的(被我更改为了接收?了)*/
//        holder.viewFind.view?.let {
//            glide(url + d.cover, it.findViewById<ImageView>(R.id.hotImage))
//        }

        //设置热点图片
        glide(url + d.cover, binding.hotImage)

        //设置热点标题
        binding.hotTitle.text = d.title

        //获取使用该布局的上下文信息
        val con = binding.root.context
//        val con = holder.viewFind.view?.context

        //给每条热点数据添加点击事件进行跳转，并将该条数据的ID传递过去
        holder.itemView.setOnClickListener{
            con?.startActivity(Intent(con, ShowNewsActivity::class.java).apply {
                putExtra("id", d.id)
            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        binding = ReItemHotBinding.inflate(LayoutInflater.from(context), parent, false)
        return BaseViewHolder(binding.root)
    }

}

















