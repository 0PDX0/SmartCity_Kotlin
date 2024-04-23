package com.example.kotlin_zhcs.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kotlin_zhcs.App
import com.example.kotlin_zhcs.App.Companion.url
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.Util.Util.glide
import com.example.kotlin_zhcs.Util.Util.show
import com.example.kotlin_zhcs.databinding.FragmentHomeBinding
import com.example.kotlin_zhcs.login.Repository.api
import com.example.kotlin_zhcs.login.Repository.coroutine
import com.example.kotlin_zhcs.ui.ShowNewsActivity
import com.example.kotlin_zhcs.ui.home.Seek.SeekActivity
import com.example.kotlin_zhcs.ui.home.adapter.HotAdapter
import com.example.kotlin_zhcs.ui.home.adapter.MoreAdapter
import com.example.kotlin_zhcs.ui.home.fragment.NewsFragment
import com.example.kotlin_zhcs.ui.home.fragment.NewsFragment.Companion.newsId
import com.google.android.material.tabs.TabLayoutMediator
import com.pdx.kotlin_zhcs.JavaSmartCityActivity
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import kotlinx.coroutines.Dispatchers

/*继承碎片类*/
class HomeFragment : Fragment() {

    private val TAG = this.javaClass.simpleName.toString()
    private lateinit var binding : FragmentHomeBinding

    //懒加载，等使用的时候再加载这个ViewModel
    private val viewModel by lazy {
        //这行代码的作用是使用 获取一个 的实例。ViewModelProviderHomeViewModel
        //this表示当前的 Activity 或 Fragment，用于提供上下文。
        //.get(HomeViewModel::class.java)是调用 的 方法来获取特定类型的 ViewModel 实例。在这里，通过传入 ，表示获取 类型的实例。
        // ViewModelProviderget()HomeViewModel::class.javaHomeViewModel
        //注意，这个获取 ViewModel 实例的操作依赖于 的作用范围。常见的作用范围包括 和 。如果使用相同的 ，那么在同一作用范围内多次获取相同类型的 ViewModel 实例会返回同一个实例。ViewModelProviderActivityFragmentViewModelStore
        ViewModelProvider(this)[HomeViewModel::class.java]
        /*获取一个ViewModel实例*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(binding.root, container, false)

        /*在碎片(Fragment)中得这么设置才行*/
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        /*将布局返回回去*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 设置动画
        setAnimation()

        // 点击进入Java版智慧城市
        startJavaSmartCity()

        //设置轮播图
        setBanner()

        //全部服务
        setMore()

        //热门主题
        setHot()

        //新闻专栏
        tabNews()

        //搜索
        seek()
    }

    /**
     * 初始化动画
     */
    private fun setAnimation() {

        // 搜索框设置动画
        binding.homeSeek.animation = App.getAnimationTopAndBottom()

        /*轮播图设置动画*/
        binding.homeBanner.animation = App.getAnimationLeftAndEnd()

        //更多服务 设置动画
        binding.HomeAnimationCardMore.animation = App.getAnimationEndAndStart()

        //Java板模块动画
        binding.javaSmartCityCardView.animation = App.getAnimationEndAndStart()

    }

    /**
     * 点击进入Java版智慧城市
     */
    private fun startJavaSmartCity() {
        binding.tvJava.setOnClickListener {
            startActivity(Intent(context, JavaSmartCityActivity::class.java))
        }
    }


    /**
     * 初始化轮播图
     */
    private fun setBanner() {
        /*设置一个装载图片网络地址资源的集合*/
        val arr = ArrayList<String>()

        //传入发送网络请求获取轮播图的请求参数
        viewModel.setBanner(mapOf(Pair("pageNum",1), Pair("pageSize", 8), "type" to 2))

        /*使用 observe() 方法将 Observer 对象附加到 LiveData 对象。observe() 方法会采用 LifecycleOwner 对象。这样会使 Observer 对象订阅 LiveData 对象，以使其收到有关更改的通知。*/
        /*这个时候的 viewModel.bannerLiveData 已经是 LiveData<Result<BannerModel>> 了*/
        viewModel.bannerLiveData.observe(viewLifecycleOwner, Observer { resources -> //Result<BannerModel>  //this -> viewLifecycleOwner
            /*获取数据是否为null*/
            /*本来是Result<BannerModel>的 但是一 getOrNull后 就变成 BannerModel了*/
            val body = resources.getOrNull()
            /*判断数据是否为null*/
            if (body != null){
                /*先清空viewModel.bannerList集合的所有数据，*/
                viewModel.bannerList.clear()
                /*再将方法中集合的所有元素 加入 viewModel定义的集合*/
                viewModel.bannerList.addAll(body.rows!!)
            }else "加载轮播图失败! ".show()

            //遍历viewModel.bannerList集合，将其中轮播图图片网络地址拼接再封装进新集合中便于Banner组件加载图片
            for (i in viewModel.bannerList){
                arr.add(url + i.advImg)
            }

            /*轮播图的相关设置*/
            binding.homeBanner.apply {

                //配置轮播图适配器
                this.setAdapter(object : BannerImageAdapter<String>(arr){
                    override fun onBindView(p0: BannerImageHolder?, p1: String?, p2: Int, p3: Int) {
                        glide(p1.toString(), p0?.imageView!!)
                    }
                })
                //设置轮播图指示器小圆点
                this.setIndicator(CircleIndicator(context))

                //设置轮播图圆角
                this.setBannerRound(25f)

                //设置轮播图样式
                //设置轮播图点击事件
                this.setOnBannerListener(object : OnBannerListener<Any>{

                    override fun OnBannerClick(p0: Any?, p1: Int) {
                        var intent : Intent = Intent(context, ShowNewsActivity::class.java)
                        when(p1) {
                            0 -> intent.putExtra("id", viewModel.bannerList.get(0).id)
                            1 -> intent.putExtra("id", viewModel.bannerList.get(1).id)
                            2 -> intent.putExtra("id", viewModel.bannerList.get(2).id)
                        }

                        startActivity(intent)
                    }

                })

            }
        })
    }

    /**
     * 初始化全部服务
     */
    private fun setMore() {
        /*用来存储网络请求返回的数据*/
        val arrMore = ArrayList<HomeMoreEntity>()

        //TODO 不使用ViewModel
        //viewModel.moreLiveData获取出来的就是网球请求返回回来的数据(LiveData<Result<MoreModel>>)
        //这里.observe看不懂，就当把 LiveData<Result<MoreModel>> 中的 Result<MoreModel> 取出来使用吧
//        viewModel.moreLiveData.observe(viewLifecycleOwner, Observer { resources -> //Result<MoreModel>
//
//            /*本来是Result<BannerModel>的 但是一 getOrNull后 就变成 BannerModel了
//            * getOrNull()：如果成功返回Value,失败返回nul*/
//            val body : MoreModel? = resources.getOrNull()                       //这个body就是请求回来封装好的数据(封装进类了)
//            if(body != null){
//                //老样子，先清空一遍集合，以防出现上次请求的数据
//                viewModel.moreList.clear()
//                //将新请求回来的集合数据放入其中
//                viewModel.moreList.addAll(body.rows!!)
//            }
//
//            //遍历封装好的集合，放入新对象集合中，方便放入适配器使用
//            for (i in viewModel.moreList){
//                //第一个参数: 服务id, 第二个参数: 服务图标(网络路径), 第三个参数: 服务图标(本地路径), 第四个参数: 服务名
//                arrMore.add(HomeMoreEntity(i.sort, url + i.imgUrl, 0, i.serviceName.toString()))
//            }
//
//            //给RecyclerView初始化，添加布局方式，适配器信息等
//            binding.homeMoreRecycler.apply {
//                this.layoutManager = GridLayoutManager(context, 5)  //布局方式网格，默认垂直排列，一行五个
//                this.adapter = MoreAdapter(
//                    true,
//                    10,
//                    this@HomeFragment,
//                    arrMore,
//                    R.layout.re_item_more
//                )
//                  //设置分隔线
////                this.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
//            }
//
//        })

        //TODO 使用协程来发起网络请求获取数据！！！！这才应该是正常的
        //开启协程  将主Main放入
        coroutine(Dispatchers.Main){
            //直接调用api获取更多服务模块数据
            api.getMore().apply {
                //判断是否获取数据成功
                if (this.code == 200){
                    //成功的话遍历数据中的服务信息 依次放入 集合中
                    for (i in this.rows!!){

                        //将遍历返回回来的数据封装进类方便给适配器使用
                        arrMore.add(
                            /*服务类*/
                            HomeMoreEntity(
                                i.sort,
                                url + i.imgUrl,
                                0,
                                i.serviceName.toString()
                            )
                        )
                    }

                    //TODO 遍历取完数据后配置适配器
                    binding.homeMoreRecycler.apply {
                        //设置它的布局方式
                        this.layoutManager = GridLayoutManager(context,5, RecyclerView.VERTICAL,  false)
                        //设置适配器
                        adapter = MoreAdapter(
                            true,
                            10,
                            this@HomeFragment,
                            arrMore,
                            R.layout.re_item_more
                        )
                        /*此代码通常在RecyclerView适配器中使用，以通知适配器特定项已更改，需要在UI中更新。*/
                        this.adapter?.notifyItemChanged(arrMore.size)
                    }
                }
            }
        }

    }

    //封装服务适配器需要使用的Bean类
    class HomeMoreEntity(
        val id : Int,
        val moreUrl : String,
        val moreUrlInt : Int,
        val moreTitle : String
    ){}

    /**
     * 初始化热门主题
     */
    private fun setHot() {
        //viewModel.newHotLiveData 类型为 LiveData<Result<NewsModel>>，这里用了个observe()里面的resources 变为了Result<NewsModel>
        //这段代码是观察newsHotLiveData的数据变化，并在数据变化时更新UI。newsHotLiveData是一个LiveData对象，用于存储新闻数据。
        //当LiveData中的数据发生变化时，Observer会被调用，传入最新的数据作为参数（在这里通过resources表示）。代码中使用了Kotlin的lambda表达式来定义Observer。
        //首先，，

        viewModel.newsHotLiveData.observe(viewLifecycleOwner, Observer { resources -> //it -> Result<NewsModel>
            //通过resources.getOrNull()方法获取到最新的数据
            val body = resources.getOrNull()
            if (body != null){
                //然后判断body是否为null。如果不为null，则清空viewModel.newsHotList列表，并将body.rows!!中的数据添加到该列表中。
                viewModel.newsHotList.clear()
                viewModel.newsHotList.addAll(body.rows!!)

                //设置热门主题的适配器
                binding.homeHot.apply {
                    /*设置网格布局管理器，垂直排列，一行两个*/
                    this.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
                    //添加适配器
                    this.adapter = HotAdapter(viewModel.newsHotList,this@HomeFragment.context, R.layout.re_item_hot)
                }
            }
        })
    }

    lateinit var arr : List<String>
    /**
     * 初始化新闻专栏
     * 这里它没有使用TabLayout + ViewPager
     * 而是TabLayout + ViewPager2
     */
    private fun tabNews() {
        //设置新闻分类集合

        arr = listOf<String>(
            "今日要闻",
            "专题聚焦",
            "政策解读",
            "经济发展",
            "文化创新",
            "科技创新"
        )

        //遍历分类集合，将其添加进TabLayout中,下面已经进行绑定了
//        for (i in arr){
//            binding.homeTabLayout.addTab(binding.homeTabLayout.newTab().setText(i))
//        }

        //设置ViewPager2
        binding.homeViewPager2.adapter = object : FragmentStateAdapter(this){

            //一共有的Pager页，item的数量
            override fun getItemCount(): Int = arr.size

            //每个Pager页都是返回NewsFragment()碎片，直接将其中的数据进行了更改
            override fun createFragment(position: Int): Fragment {
                val fg = when(position){
                    0 -> NewsFragment()
                    1 -> NewsFragment()
                    2 -> NewsFragment()
                    3 -> NewsFragment()
                    4 -> NewsFragment()
                    else -> NewsFragment()
                }

                return fg
            }

            //加载每个页面每条数据时都会调用
            override fun getItemId(position: Int): Long {
                //修改1
                newsId = when(position){
                    0 -> 9
                    1 -> 17
                    2 -> 19
                    3 -> 20
                    4 -> 21
                    else -> 22
                }

                return super.getItemId(position)
            }

        }

        //只需要一个这个就能将TabLayout 与 ViewPager2 绑定了
        TabLayoutMediator(binding.homeTabLayout, binding.homeViewPager2){   tab, positon ->
            tab.text = arr[positon]
//            tab.customView = getViewAtI(positon)
        }.attach()

        //监听ViewPager2的滑动切换TabLayout标签
//        binding.homeViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
//            //滑动后调用，position为新页面的index
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                //每次滑动都要对TabLayout进行选择
//                binding.homeTabLayout.getTabAt(position)?.select()
//            }
//        })
//
//        //监听TabLayout点击，切换ViewPager2页面
//        binding.homeTabLayout.tabMode = TabLayout.MODE_SCROLLABLE   //设置TabLayout横向可滚动的
//
//        binding.homeTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                binding.homeViewPager2.currentItem = tab?.position!!
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {}
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {}
//
//        })

    }

    /**
     * 首页新闻 模糊查询 初始化搜索
     * onEditorAction参数说明
       1、textView 编辑框输入的内容，比如点击了换行也会执行换行后的输出内容
       2、id 事件标识，其值与android:imeOptions属性设置的参数有关，与EditorInfo.IME_*做匹配
       3、keyEvent 触发事件，与KeyEvent.ACTION_*做匹配（备注：keyEvent对象有可能会为null，原因待研究）
       4、返回值 true表示不关闭软键盘，false表示关闭软键盘
     */
    private fun seek() {
        binding.homeSeek.setOnEditorActionListener(object : TextView.OnEditorActionListener{

            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (binding.homeSeek.text.toString().isEmpty()){
                        "您还没有输入内容！".show()
                    }else {
                        startActivity(Intent(context, SeekActivity::class.java).apply {
                            putExtra("content", binding.homeSeek.text.toString())
                        })
                    //清空输入框
                    binding.homeSeek.setText("")
                    }
                }
                return false
            }

        })
    }

//    private fun getViewAtI(position : Int) : View {
//        var binding : TabItemNewsBinding = TabItemNewsBinding.inflate(layoutInflater, null ,false)
//
//        var view : View = binding.root
//
//        var img : ImageView = binding.img
//        val tv : TextView = binding.tv
//
//        img.setImageResource(R.mipmap.smartcity1)
//        tv.text = arr[position]
//
//        return view
//    }
}








































