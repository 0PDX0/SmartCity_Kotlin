package com.example.kotlin_zhcs.login.network

import com.example.kotlin_zhcs.login.model.*
import okhttp3.RequestBody
import retrofit2.http.*

interface AppApi {

    companion object{
        //安全类型
        private const val Safety = "Authorization"

        //查询某个问诊中的所有聊天
        var ApiId : Int? = null
    }

    //用户登录
    @POST("/prod-api/api/login")
    suspend fun postLogin(@Body body: RequestBody) : LoginModel

    //用户注册
    @POST("/prod-api/api/register")
    suspend fun postSign(@Body body: RequestBody) : Any

    //修改个人基本信息
    @PUT("/prod-api/api/common/user")
    suspend fun putUserInfo(@Header("Authorization") token : String, @Body body: RequestBody) : Any

    //获取轮播信息
    @GET("/prod-api/api/rotation/list")
    /*TODO suspend是协程中很重的关键字，它用来修饰函数，表示此函数是一个会挂起的函数，并且 挂起函数只有在协程中使用或者被另一个挂起函数调用*/
    suspend fun getBanner(@QueryMap mp : Map<String, Int>) : BannerModel
    /*@QueryMap	与@Query注解一样，只不过参数对象是一个Map集合，会Map集合转换为键值对。*/

    //获取全部服务
    @GET("/prod-api/api/service/list")
    suspend fun getMore() : MoreModel

    //根据指定的类名ID获取服务
    @GET("/prod-api/api/service/list")
    suspend fun getMoreType(@Path("serviceType") serviceType : String) : MoreModel
    //@Path	标志参数是用来替换请求路径的  serviceType(服务类别)

    //获取热门主题
    @GET("/prod-api/press/press/list")
    suspend fun getHot(@Query("hot") hot : String? , @Query("type") type : String?) : NewsModel
    /*@Query 标志参数（@Query(“key1”) String value1）会追加在请求路径后面（?key1=value1）*/

    //获取新闻分类
    @GET("/prod-api/press/category/list")
    suspend fun getCategory() : NewsTabModel

    //获取新闻详细信息
    @GET("/prod-api/press/press/{id}")
    suspend fun getNewsYes(@Path("id") id : Int) : NewsShowModel

    //获取新闻评论列表
    @GET("/prod-api/press/comments/list")
    suspend fun getNewsComment(@Query("newsId") newsId : Int) : NewsComment

    //新闻点赞
    @PUT("/prod-api/press/press/like/{id}")
    suspend fun putNewsLike(@Header("Authorization") token : String, @Path("id") id : Int) : Any

    //发布新闻评论
    @POST("/prod-api/press/pressComment")   //TODO
    suspend fun postNewsComment(@Header("Authorization") token : String, @Body body : RequestBody) : Any

    //获取用户基本信息
    @GET("/prod-api/api/common/user/getInfo")
    suspend fun getUserInfo(@Header("Authorization") token : String) : UserInfoModel

}

















