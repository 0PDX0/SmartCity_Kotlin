package com.example.kotlin_zhcs.ui.home.adapter

import android.content.Intent
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.kotlin_zhcs.App
import com.example.kotlin_zhcs.App.Companion.url
import com.example.kotlin_zhcs.Base.BaseRecyclerAdapter
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.Util.Util.glide
import com.example.kotlin_zhcs.login.model.NewsModel
import com.example.kotlin_zhcs.ui.ShowNewsActivity


/**
 * 新闻列表填充
 */
class NewsAdapter (
    private val list: MutableList<NewsModel.RowsBean>,
    layoutId : Int
) : BaseRecyclerAdapter<NewsModel.RowsBean>(list, layoutId) {

//    companion object{

        lateinit var newsImage : ImageView
        lateinit var newsTitle : TextView
        lateinit var newsContent : TextView
        lateinit var newsTime : TextView
        lateinit var newsUserSize : TextView

        fun initView(view : View?){
            if (view != null){
                newsImage = view.findViewById<ImageView>(R.id.newsImage)
                newsTitle = view.findViewById<TextView>(R.id.newsTitle)
                newsContent = view.findViewById<TextView>(R.id.newsContent)
                newsTime = view.findViewById<TextView>(R.id.newsTime)
                newsUserSize = view.findViewById<TextView>(R.id.newsUserSize)
            }
        }
//    }

    override fun setData(
        view: BaseViewHolder.ViewFind,
        data: NewsModel.RowsBean,
        position: Int,
        holder: BaseViewHolder
    ) {

        initView(view.view)

        //设置动画
        holder.itemView.animation = App.getAnimationBottomAndTop()

        //获取数据
        val d = list[position]

        //将数据填充到UI上
        glide(url + d.cover, newsImage)
        newsTitle.text = d.title
        newsContent.text = Html.fromHtml(d.content, Html.FROM_HTML_MODE_COMPACT)
        newsTime.text = d.publishDate

        val si = d.likeNum.toString() + "评论"
        newsUserSize.text = si

        val con = holder.viewFind.view?.context

        //点击查看新闻详细
        holder.itemView.setOnClickListener{
            con?.startActivity(Intent(con, ShowNewsActivity::class.java).apply {
                putExtra("id", d.id)
            })
        }
    }

}
























































