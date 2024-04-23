package com.example.kotlin_zhcs.login

import androidx.lifecycle.MutableLiveData
import com.example.kotlin_zhcs.login.model.BannerModel
import com.example.kotlin_zhcs.login.model.MoreModel
import com.example.kotlin_zhcs.login.network.AppApi
import com.example.kotlin_zhcs.login.network.ServiceNET.Companion.create
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 *  处理网络回调
 **/
object Repository {

    /*这里封装了进行网络请求的功能操作*/
    /*这个create方法返回的就是它的泛型*/
    val api : AppApi = create<AppApi>()

    /**
     * 获取轮播图
     * 请求参数就是方法的参数 map
     */
    fun getBanner(map : Map<String, Int>) : MutableLiveData<Result<BannerModel>> = fire(Dispatchers.IO){  //Dispatchers.IO：协程调度器
        /*此括号中的内容是fire方法的第二个参数的lambda表达式*/

        //调用封装好的网络请求类，使用getBanner(map(请求参数,@QueryMap：Map类型的键值对，跟在请求地址后面))
        //返回值就是请求回来的数据(封装进类中)
        val place : BannerModel = api.getBanner(map)
        //判断状态码，是否请求成功
        if (place.code == "200"){
            Result.success(place)   //这段话是fire方法第二个参数的lambda表达式的展开，最后一行就是这个方法的返回值，是一个Result<T>，泛型就是place的类型
        }else{
            Result.failure(RuntimeException("加载轮播图出错!"))
        }
    }

    /**
     * 获取全部服务
     */
    fun getMore() = fire(Dispatchers.IO) {
        //获取网络请求返回回来的信息
        val place : MoreModel  = api.getMore()
        //判断是否请求成功
        if (place.code == 200){
            /*请求成功则将其封装为Result<MoreModel>作为 fire 方法第二个参数*/
            Result.success(place)
        }else {
            Result.failure(RuntimeException("加载全部服务出错! "))
        }
    }

    /**
     * 获取热门主题
     */
    fun getNews(hot : String?, type : String?) = fire(Dispatchers.IO){  //fire()方法最终返回值MutableLiveData<Result<NewsModel>>
        //获取网络请求返回回来的信息(根据清空传参)
        val place = api.getHot(hot, type)
        //判断请求是否成功
        if(place.code == 200){
            /*请求成功则将其封装为Result<NewsModel>作为 fire 方法第二个参数*/
            Result.success(place)
        }else{
            Result.failure(RuntimeException("加载热门主题出错！"))
        }
    }

    //-----------------------------------------------------------------------


    /**
     * 封装自动构建返回LiveData
     *
    这是一个私有函数 ，它使用协程 CoroutineScope 在指定的 CoroutineContext 中执行给定的 suspend 函数，并返回一个 MutableLiveData 对象，用于观察协程执行的结果。fire()
    函数签名如下：
    private fun <E> fire(
    context: CoroutineContext,
    block: suspend () -> Result<E>
    ): MutableLiveData<Result<E>> {
    // 函数实现
    }
    context: CoroutineContext是一个 CoroutineContext 对象，用于执行协程。它可以是主线程的 CoroutineContext，也可以是自定义的 CoroutineContext，用来指定协程执行的线程或调度器。
    block: suspend () -> Result<E>是一个挂起函数类型的参数，用于执行具体的业务逻辑。它通过 关键字来标记为挂起函数，表示该函数可能会暂停执行并等待其他操作的完成。是一个简单的封装类型，表示业务逻辑的结果。suspendResult<E>
    函数内部的实现流程如下：
    创建一个 MutableLiveData 对象 ，用于存储协程执行的结果。liveData
    创建一个 CoroutineScope 对象 ，用于协程的作用域。scope
    在协程作用域中启动一个新的协程，使用 函数来执行业务逻辑。launch
    在协程中使用 进行异常处理。如果业务逻辑执行成功，通过 来调用 函数，获取业务逻辑的结果，并使用 将结果发布到 对象中。try-catchblock.invoke()blockliveData.postValue(result)liveData
    如果业务逻辑执行过程中发生异常，通过 将异常结果发布到 对象中。liveData.postValue(Result.failure(e))liveData
    返回 对象，供外部观察协程执行结果。liveData
    该函数允许通过指定的 CoroutineContext 在后台线程执行业务逻辑，并将结果以 LiveData 的形式返回，以方便在 Android 中进行观察和处理。
     */
    private fun <E> fire(
        context : CoroutineContext,
        black : suspend () -> Result<E> // block: suspend () -> Result<E>是一个挂起函数类型的参数，用于执行具体的业务逻辑。它通过 关键字来标记为挂起函数，表示该函数可能会暂停执行并等待其他操作的完成。
    ) : MutableLiveData<Result<E>> {
        val liveData = MutableLiveData<Result<E>>() //MutableLiveData 对象 ，用于存储协程执行的结果。
        val scope = CoroutineScope(context)         //CoroutineScope是启动协程的作用域，所有协程都需要在作用域中启动，并且作用域内部创建子协程则会自动传播给子协程；
                                                    //而CoroutineContext则是在协程作用域中执行的线程切换。

        //开启协程
        scope.launch {
            /*因为此方法的第二个参数返回值Result中有可能是封装了一个异常进去，这里需要将其抛出*/
            try {
                /*这里实例化了调用了此方法的第二个参数lambda表达式的返回值，将请求返回回来的数据实例化了出来*/
                val result : Result<E> = black.invoke() //callback?.invoke(default) 相当于 callback( default ),这里就相当于调用了Result<E>的构造函数,返回值确认了也是Result<E>
                liveData.postValue(result)              //将结果发布到对象中
            }catch (e : Exception){
                liveData.postValue(Result.failure(e))   //将报错信息发布到对象中
            }
        }

        return liveData //将最后处理好的结果(MutableLiveData<Result<E>>)返回回去
    }

    /**
     * 协程封装
     * 该函数采用两个参数：coroutine
        context1（可选）：用于指定用于运行协程的调度程序。默认情况下，它使用调度程序。CoroutineContext Dispatchers.IO
        black：表示协程块的可挂起的 lambda 表达式。
        该函数返回一个对象，该对象表示正在运行的协程。Job
        在函数中，使用提供的调度程序创建。调用该函数以启动协程。CoroutineScopecontext1 launchCoroutineScope
        在协程内部，块用于创建新的子协程作用域。这用于标记将作为单独的协程执行的代码块。然后，在这个新的协程作用域内调用 lambda 表达式。coroutineScopeblack
        在协程中抛出的任何异常都会被捕获并使用 进行打印。e.printStackTrace()
     */
    fun coroutine(
        context1 : CoroutineContext = Dispatchers.IO,
        black: suspend CoroutineScope.() -> Unit
    ) : Job {
        return CoroutineScope(context1).launch {
            try {
                coroutineScope {
                    black()
                }
            } catch (e : Exception){
                //捕获协程中出现的所有异常
                e.printStackTrace()
            }
        }
    }

}
























