package com.example.kotlin_zhcs.ui

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kotlin_zhcs.App.Companion.getToken
import com.example.kotlin_zhcs.App.Companion.url
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.Util.CheckLoginUtils
import com.example.kotlin_zhcs.Util.Util.show
import com.example.kotlin_zhcs.databinding.ActivityShowNewsBinding
import com.example.kotlin_zhcs.login.Repository.api
import com.example.kotlin_zhcs.login.Repository.coroutine
import com.example.kotlin_zhcs.ui.notifications.NotificationsFragment
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class ShowNewsActivity : AppCompatActivity() {

    var id : Int = 0

    lateinit var binding : ActivityShowNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.extras!!.getInt("id")

        initNewsShow(id)

        //临时使用
//        var token : String = "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6ImExZWUwZWQxLTA2NmEtNGFiYy1iODMwLTg0MmRmNWM1OTJlMyJ9.BzV2tXCy9jtFlMBgR-_1sCAO46YuFntTvEi6v4Rb_DgDD3eBuY9B07ar_yzSD0EhuvQiABk2Vm2MN0JpVAGQXg"

        binding.showNewsLike.setOnClickListener {

            CheckLoginUtils.isLogin(this, true, object : NotificationsFragment.CheckLogin{
                override fun onAlready() {
                }
                override fun onNone() {
                }
            })

            coroutine(Dispatchers.Main) {
                //TODO 2023-11-30
                var re : Any = api.putNewsLike(getToken(), id)

                if (re.toString().indexOf("200") != -1){
                    //Toast也只能在主线程调用
                    "点赞成功".show()
                }

                initNewsShow(id)
            }

        }

        binding.play.setOnClickListener {

            CheckLoginUtils.isLogin(this, true, object : NotificationsFragment.CheckLogin{
                override fun onAlready() {
                }
                override fun onNone() {
                }
            })

            var mediaType : MediaType? = "application/json;charset=utf-8".toMediaTypeOrNull()

            var map = mapOf<String,Any>(
                "newsId" to id,
                "content" to binding.et.text.toString()
            )

            Log.e("pdx1",map.toString())                //{newsId=35, content=111}
            Log.e("pdx2",JSONObject(map).toString())    //{"newsId":35,"content":"111"}

            var a : RequestBody = JSONObject(map).toString().toRequestBody(mediaType)

            coroutine(Dispatchers.Main) {
                var a = api.postNewsComment(getToken(), a)

                if (a.toString().contains("200")){
                    initNewsShow(id)
                    "评论成功".show()
                    binding.et.setText("")
                }else{
                    "评论失败".show()
                }
            }
        }
    }

    /**
     * 初始化新闻详细
     */
    private fun initNewsShow(id: Int) {
        coroutine(Dispatchers.Main){
            //TODO 这里很奇怪，先发送请求加载新闻数据后发送请求加载评论不会生效，互换后又两个都能加载
//            var mData = api.getNewsComment(id)
//
//            if (mData.code == 200){
//                var data = mData.rows!!
//
//                binding.showNewsCommentRecyc.apply {
//                    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
//                        context,
//                        androidx.recyclerview.widget.RecyclerView.VERTICAL,
//                        false
//                    )
//
//                    adapter = com.example.kotlin_zhcs.ui.home.adapter.NewsCommentAdapter(
//                        data,
//                        context,
//                        R.layout.item_news_comment
//                    )
//                }
//            }

            var YData = api.getNewsYes(id)

            if (YData.code == 200){
                var data = YData.data!!

                binding.showNewsTitle.text = data.title

                binding.showNewsDate.text = data.createTime

                Glide.with(this@ShowNewsActivity).load(url + data.cover)
                    .error(R.mipmap.ic_launcher).into(binding.showNewsImg)

                binding.showNewsContent.text = Html.fromHtml(data.content, Html.FROM_HTML_MODE_COMPACT)

                binding.showNewsLook.text = data.readNum.toString()

                binding.showNewsLike.text = data.likeNum.toString()

//                binding.showNewsComment.text = "评论 ${mData.total}"

            }
        }

        coroutine(Dispatchers.Main){
            var mData = api.getNewsComment(id)

            if (mData.code == 200){
                var data = mData.rows!!

                //sortByDescending：降序
                //sortBy：升序 没用
                data.sortByDescending { it.commentData.toString() }

                binding.showNewsCommentRecyc.apply {
                    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                        context,
                        androidx.recyclerview.widget.RecyclerView.VERTICAL,
                        false
                    )

                    adapter = com.example.kotlin_zhcs.ui.home.adapter.NewsCommentAdapter(
                        data,
                        context,
                        R.layout.item_news_comment
                    )
                }

                binding.showNewsComment.text = "评论 ${mData.total}"
            }
        }

    }



    /**
     * 结束当前页面
     */
    fun back(view: View) {
        finish()
    }
}









/*
   fun stringToDate(dateString : String ) : Date {
        //从第一个字符开始解析
        val position = ParsePosition(0);
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val dateValue = simpleDateFormat.parse(dateString,position);
        return dateValue;
    }*/