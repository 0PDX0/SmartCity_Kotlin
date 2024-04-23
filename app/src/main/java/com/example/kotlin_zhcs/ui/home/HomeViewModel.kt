package com.example.kotlin_zhcs.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.kotlin_zhcs.login.Repository
import com.example.kotlin_zhcs.login.model.BannerModel
import com.example.kotlin_zhcs.login.model.MoreModel
import com.example.kotlin_zhcs.login.model.NewsModel

class HomeViewModel : ViewModel() {

    //这个用来存储用来请求轮播图信息的请求参数
    private val banner = MutableLiveData<Map<String, Int>>()
    /*这个用来存储请求回来的轮播图数据信息*/
    val bannerList = ArrayList<BannerModel.RowsBean>()

    /*返回值应该是MutableLiveData<Result<BannerModel>>, 变为了LiveData<Result<BannerModel>>*/
    /*switchMap方法接收两个参数：第一个参数是一个LiveData对象，第二个参数是一个函数（或者Lambda表达式），
    该函数根据第一个参数的值返回一个新的LiveData对象。当第一个LiveData对象的值发生变化时，
    switchMap方法会自动触发函数的调用，并将新的LiveData对象作为结果返回。*/
    val bannerLiveData: LiveData<Result<BannerModel>> = Transformations.switchMap(banner){ query ->    //it = Map<String, Int>?
        Repository.getBanner(query)
    }

    //将用户传递过来封装好的集合传递给这个HomeViewModel类的专门存储轮播图的集合变量
    fun setBanner(mp : Map<String, Int>){
        banner.value = mp
    }

    //全部服务
    private val more = MutableLiveData<String>().apply {
        postValue("test")
    }

    var moreList = ArrayList<MoreModel.RowsBean>()

    val moreLiveData /*: LiveData<Result<MoreModel>>*/ = Transformations.switchMap(more){
        Repository.getMore()
    }

    //热门主题
    private val newsHot = MutableLiveData<String>().apply {
        postValue("12") //测试，
        postValue("Y")  //以最后一次postValue的值为准
    }
    val newsHotList = ArrayList<NewsModel.RowsBean>()

    val newsHotLiveData : LiveData<Result<NewsModel>> = Transformations.switchMap(newsHot){ query -> //it String 这里的query就是Transformations.switchMap()的参数,这里是post了一个Y进去
        Log.e("pdx", query)
        /*这里是需要热门新闻主题，所以需要一个参数Y传入*/
        Repository.getNews(query, null)
    }

    //热门主题
    private val news = MutableLiveData<String>()
    val newsList = ArrayList<NewsModel.RowsBean>()

    val newsLiveData = Transformations.switchMap(news){ query -> //it String!
        Repository.getNews(null, query)
    }

    fun setNewsType(type : String?){
        news.value = type
    }


}



















