package com.example.kotlin_zhcs.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kotlin_zhcs.App.Companion.url
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.Util.Util.show
import com.example.kotlin_zhcs.databinding.FragmentNewsBinding
import com.example.kotlin_zhcs.login.Repository.api
import com.example.kotlin_zhcs.login.Repository.coroutine
import com.example.kotlin_zhcs.ui.ShowNewsActivity
import com.example.kotlin_zhcs.ui.home.HomeViewModel
import com.example.kotlin_zhcs.ui.home.adapter.NewsAdapter
import com.google.android.material.tabs.TabLayout
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import kotlinx.coroutines.Dispatchers

class NewsFragment : Fragment() {

    lateinit var binding : FragmentNewsBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBanner()

        initTabLayoutOrRecyclerView()
    }

    /**
     * 初始化导航条与新闻数据
     */
    private fun initTabLayoutOrRecyclerView() {

        val titles = mutableListOf<String>()

        //它会先将方法内部执行完毕再执行这段代码，注意这里有参数Dispatcher.Main 在主线程上活动，
        // Dispatcher.IO主要用于发送网络请求等，不能使用他来更新UI
        coroutine(Dispatchers.Main){
            //获取请求返回的新闻分类数据
            val arr = api.getCategory()
            if (arr.code == 200){
                for (i in arr.data!!){
                    titles.add(i.name!!)
                    binding.newsTab.addTab(binding.newsTab.newTab().setText(i.name))
                }

                binding.newsTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        if (tab != null){
                            setNews(arr.data!![tab.position].id)
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {}
                    override fun onTabReselected(tab: TabLayout.Tab?) {}
                })

                setNews(arr.data!![0].id)
            }
        }
    }

    private fun setNews(typeId : Int?){

        viewModel.setNewsType(typeId.toString())
        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer { resources ->
            val body = resources.getOrNull()
            if (body != null){
                viewModel.newsList.clear()
                viewModel.newsList.addAll(body.rows!!)

                //适配器
                binding.newsRecyc.apply {
                    this.layoutManager = LinearLayoutManager(context)
                    this.adapter = NewsAdapter(viewModel.newsList, R.layout.re_item_news)
                }
            }
        })

    }

    /**
     * 初始化轮播图
     */
    private fun initBanner() {
        //设置一个装载网络图片地址资源的集合
        val arr = ArrayList<String>()

        //传入发送网络请求获取轮播图的请求参数
        viewModel.setBanner(mapOf(Pair("type",2))) //"type" to 2

        //传入发送网络请求获取轮播图的请求参数
        viewModel.bannerLiveData.observe(viewLifecycleOwner, Observer {   resources ->  //it:Result<BannerModel>
            //获取数据是否为null
            val body = resources.getOrNull()
            //判断数据是否为Null
            if (body != null){
                //先清空viewModel.bannerList集合的所有数据
                viewModel.bannerList.clear()
                //再将方法中集合的所有元素 加入 viewModel 定义的集合
                viewModel.bannerList.addAll(body.rows!!)
            }else "加载轮播图失败".show()

            //遍历viewModel.bannerList集合，将其中轮播图图片网络地址拼接再封装进新集合
            for ((index,i) in viewModel.bannerList.withIndex()){
                arr.add(url + i.advImg)
            }

            //轮播图的相关设置
            binding.newsBanner.apply {
                setAdapter(object : BannerImageAdapter<String>(arr){
                    override fun onBindView(p0: BannerImageHolder?, p1: String?, p2: Int, p3: Int) {
                        Glide.with(this@NewsFragment).load(p1).into(p0!!.imageView)
                    }

                })

                setLoopTime(4000)

                indicator = CircleIndicator(context)

                //设置轮播图点击跳转事件
                setOnBannerListener(object : OnBannerListener<Any>{

                    override fun OnBannerClick(p0: Any?, p1: Int) {
                        val intent : Intent = Intent(context, ShowNewsActivity::class.java)
                        when(p1) {
                            0 -> intent.putExtra("id", viewModel.bannerList.get(0).id)
                            1 -> intent.putExtra("id", viewModel.bannerList.get(2).id)
                            2 -> intent.putExtra("id", viewModel.bannerList.get(3).id)
                        }
                        startActivity(intent)
                    }

                })
            }

        })
    }


}


















