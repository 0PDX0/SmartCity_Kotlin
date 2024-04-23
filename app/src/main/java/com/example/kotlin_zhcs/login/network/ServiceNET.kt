package com.example.kotlin_zhcs.login.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络访问请求封装
 */
class ServiceNET {

    companion object{
        //服务器地址
        private const val BASE_URL = "http://124.93.196.45:10001/"

        /*
        注：baseUrl("")中的url必须以’/'结尾，否则会报异常；@GET(“public”)中的url如果是需要拼接在baseUrl之后的则不要以‘/’开头
         */
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)                                  //设置网络请求的Url地址
            .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器 //GsonConverterFactory导入了converter-gson-2.6.3.jar包
            .build()

        //通过.Class对象进行网络请求,这里传入的service : Class<E> 是发送网络请求给服务器所使用的类
        fun <E> create(service : Class<E>) : E = retrofit.create(service)

        //以泛型的方式进行网络请求：create<接口对象>()
        //reified是一个特殊的关键字，用于修饰内联函数中的类型参数。这使得在函数内部可以访问类型参数的具体类型,让函数内部也可以使用这个泛型<E>
        //在这个方法里又调用了这个类的另一个有参的create方法，将retrofit构建完成的网络请求类返回了回来
        inline fun <reified E> create() : E = create(E::class.java)
    }

}




























