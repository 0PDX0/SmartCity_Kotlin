package com.example.kotlin_zhcs.ui.guide

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.kotlin_zhcs.App.Companion.context
import com.example.kotlin_zhcs.App.Companion.isStart
import com.example.kotlin_zhcs.MainActivity
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.Util.ToastUtil
import com.example.kotlin_zhcs.Util.Util.show
import com.example.kotlin_zhcs.databinding.ActivityGuideBinding
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener

//import kotlinx.android.synthetic.main.activity_guide.*


class GuideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuideBinding

//    var btn_ok: Button? = null
//    var btn_net: Button? = null
    var banner_guide: Banner<Int, BannerImageAdapter<Int>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //隐藏标题栏 supportActionBar?.hide()  //不改Xml文件，这个也能隐藏标题栏

        binding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root) //如果使用了binding.ID这里就必须使用binding.root

        initView();

        binding.guideNET.apply {
            setOnClickListener {
                alert()
            }
        }

        bannerGuide()
    }

    /**
     * 初始化视图
     */
    private fun initView() {

        banner_guide = findViewById(R.id.guide_banner)
//
//        btn_ok = findViewById<Button>(R.id.guideOK)
//
//        btn_net = findViewById<Button>(R.id.guideNET)

    }

    /**
     * 引导页
     */
    private fun bannerGuide() {

        //存储引导页图片
        val arrGuide = ArrayList<Int>().apply {
            this.add(R.mipmap.smartcity1)
            this.add(R.mipmap.smartcity2)
            this.add(R.mipmap.smartcity3)
            this.add(R.mipmap.smartcity4)
            this.add(R.mipmap.smartcity5)
        }

        //使用Banner做引导页
        //在xml布局添加app:banner_infinite_loop = "false"这行，使轮播图不能无限循环
        binding.guideBanner?.apply {
            setAdapter(object : BannerImageAdapter<Int>(arrGuide) {
                override fun onBindView(p0: BannerImageHolder?, p1: Int?, p2: Int, p3: Int) {
                    Glide.with(this@GuideActivity).load(p1)
                        .transform(CenterCrop()).into(p0!!.imageView)
                }
            })
            /*两个都可以生产小圆点指示器*/
            this.addBannerLifecycleObserver(this@GuideActivity).indicator = CircleIndicator(context)
//            indicator = CircleIndicator(context)

            addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

                }

                override fun onPageSelected(p0: Int) {
                    if (p0 == 4) {
                        binding.guideNET.visibility = View.VISIBLE
                        binding.guideOK.visibility = View.VISIBLE
                    } else {
                        binding.guideNET.visibility = View.GONE
                        binding.guideOK.visibility = View.GONE
                    }
                }

                override fun onPageScrollStateChanged(p0: Int) {

                }

            })
        }

        binding.guideOK.setOnClickListener {
            getSharedPreferences("token", Context.MODE_PRIVATE).apply {
                val http = getString("http", "").toString() //toString后，显式指定它的类型就不用String?了
                if (http.isEmpty()){
                    "请先配置网络设置".show()
                }else {
                    getSharedPreferences("token", Context.MODE_PRIVATE)
                        .edit().putBoolean("true", true).apply()
                    startActivity(Intent(this@GuideActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }




    /**
     * 网络配置
     */
    private fun alert() : Unit{
        runOnUiThread {
            //获取布局
            val layout = View.inflate(this, R.layout.guidenet, null)
            //获取控件
            val ed = layout.findViewById<EditText>(R.id.guideEditText)

            //弹窗让用户填写服务器IP地址
            AlertDialog.Builder(this).apply {
                setTitle("配置IP地址")      //设置标题
                setView(layout)           //设置中心的布局
                setPositiveButton("确定") { text, onClick ->  //设置确认按钮
                    /*如果用户未输入IP地址，就使用默认的准备好的IP*/
                    if (ed.text.toString().isEmpty()) {
                        getSharedPreferences("token", Context.MODE_PRIVATE).edit()
                            .putString("http", "124.93.196.45:10001").apply()
                    } else {
                    /*用户输入了IP地址就使用用户输入的*/
                        getSharedPreferences("token", Context.MODE_PRIVATE).edit()
                            .putString("http", ed.text.toString()).apply()
                    }
                }

                setNegativeButton("取消") { text, onClick ->
                    ToastUtil.show(this@GuideActivity, "$text$onClick")
                }

                /*！！！别忘了显示*/
                show()
            }

        }
    }

    override fun onStart() {
        super.onStart()
        //如果已经登录过，则直接进入主页
        if(isStart()){
            startActivity(Intent(context, MainActivity::class.java))
            finish()
        }
    }

}







/*binding.guideBanner?.apply {
            setAdapter(object : BannerImageAdapter<Int>(arrGuide){
                override fun onBindView(p0: BannerImageHolder?, p1: Int?, p2: Int, p3: Int) {
                    Glide.with(this@GuideActivity).load(p1).error(R.mipmap.ic_launcher)
                        .transform(CenterCrop()).into(p0!!.imageView)
//                    Glide.with(this@GuideActivity).load(R.mipmap.smartcity1)
//                        .transform(CenterCrop()).into(p0!!.imageView)
                }
            })
        }*/





