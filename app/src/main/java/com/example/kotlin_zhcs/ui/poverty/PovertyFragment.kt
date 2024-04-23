package com.example.kotlin_zhcs.ui.poverty

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.databinding.FragmentPovertyBinding
import com.example.kotlin_zhcs.ui.poverty.adapter.PtHotAdapter
import com.example.kotlin_zhcs.ui.poverty.adapter.PtMoreAdapter
import com.example.kotlin_zhcs.ui.poverty.adapter.PtNewsAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.lang.reflect.Type

/**
 * 精准扶贫模块开发
 * 此模块需要自己设计 UI 开发
 */
class PovertyFragment : Fragment() {

    val img_banner = listOf<Int>(
        R.drawable.support_banner1,
        R.drawable.support_banner2,
        R.drawable.support_banner3,
        R.drawable.support_banner4,
        R.drawable.support_banner5
    )

    val img_more = listOf<Int>(
        R.mipmap.po1,
        R.mipmap.po2,
        R.mipmap.po3,
        R.mipmap.po5
    )

    val title_more = listOf<String>(
        "扶贫案例",
        "村情村貌",
        "收到求助",
        "案例发布"
    )

    val img_news = mutableListOf<Int>(
        R.mipmap.support_press1,
        R.mipmap.support_press2,
        R.mipmap.support_press4,
        R.mipmap.support_press5,
        R.mipmap.support_press6,
        R.mipmap.support_press7,
        R.mipmap.support_press8,
        R.mipmap.support_press9,
        R.drawable.support_banner1,
        R.mipmap.pension_org10,
        R.mipmap.pension_org11,
    )

    companion object {
        val PovertyList = mutableMapOf<String, String>(
            "汝南县板店乡：精准扶贫 入户走访暖人心" to "“最近家里情况怎么样，发展养殖业有没有困难.......”11月8日，汝南县板店乡党委书记王留印来到该乡刘营村脱贫户马忠于家走访，与马忠于夫妇亲切交谈。",
            "精准扶贫零距离 入户走访暖人心" to "“最近生活怎么样啊？”“一年能挣多少钱”“家里有什么困难吗？”“孩子学习怎么样”……结对帮扶的贫困户家里情况怎么样，日子过得好不好，桩桩件件的小事，成了夏河县人民法院领导们心头的挂念。",
            "入户走访贫困户，精准扶贫暖人心" to "10月12日，在第七个国家扶贫日即将来临之际，矿区法院党组书记、院长姚胜利参加了省法院在临夏县尹集镇大滩村举行的教育扶贫2020年“天平助学金”捐助仪式，为临夏县莘莘学子送上金秋最温暖的关怀。",
            "精准扶贫在行动 走访入户暖人心" to "10月17日是我国第7个扶贫日，也是第28个国际消除贫困日。推进脱贫攻坚是深入扎实开展持久的“精准扶贫”活动的重要工作内容。\n" +
                    "10月15日下午，湖北三峡技师学院党委书记胡玉梅，副书记、院长周玉堂，带领学院七个支部党员及党外知识分子代表一行86人，前往点军三岔口扶贫点，开展“弘扬伟大抗疫精神，决胜脱贫攻坚”主题党日活动。\n"
        )
    }

    lateinit var binding : FragmentPovertyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPovertyBinding.inflate(inflater, container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*初始化轮播图信息*/
        initBanner()

        /*初始化服务入口信息*/
        initMore()

        /*初始化入户走访信息*/
        initHot()

        /*初始化新闻入口*/
        initNews()
    }

    /**
     * 初始化新闻入口
     */
    private fun initNews() {
        val mData : String = getData(context, R.raw.support_press)!!
        //Kotlin中使用 TypeToken 的构造函数需要加上 object
        val lisType : Type = object : TypeToken<List<PtNewsEntity>>(){}.type

        val list : ArrayList<PtNewsEntity> = Gson().fromJson(mData, lisType)

        binding.ptNewsRV.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL , false)

            adapter = PtNewsAdapter(
                list,
                img_news,
                context,
                R.layout.re_item_poverty_news
            )

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

    }

    /*TODO 可读出本地文本文件的所有字符*/
    fun getData(context: Context?, id: Int): String? {
        //这一行打开一个由id参数指定的原始资源文件的输入流。getResources()方法返回与给定上下文关联的Resources对象，openRawResource()方法打开指定资源ID的输入流。
        val inputStream = context?.resources?.openRawResource(id)
//        val file = File("D:\\Android\\AndroidStudioProjects\\Kotlin_ZHCS\\app\\src\\main\\res\\raw\\support_press")
//        val cs1 : String = file.readText()
        try {
            //这一行创建一个BufferedReader对象，从输入流中读取数据。它使用InputStreamReader将输入流中的字节转换为使用UTF-8编码的字符。
            val reader = BufferedReader(InputStreamReader(inputStream, "utf-8"))

            //这一行创建了一个StringBuilder对象，该对象用于通过将多个较小的字符串附加在一起来有效地构建字符串。
            val sb = StringBuilder()
            var line: String? = null

            //while ((line = reader.readLine()) != null){…}:这一行开始一个while循环，
            // 只要还有更多行要从BufferedReader中读取，循环就会继续。在循环的每次迭代中，
            // 它使用readLine()方法从BufferedReader中读取一行，并将其赋值给“line”变量。
            // 如果该行不为空，则执行循环体。
            while (reader.readLine().also { line = it } != null) {
                //该行将当前行追加到StringBuilder对象"sb"。
                sb.append(line)
            }
//            Log.e("pdx", cs1)
            //这一行关闭输入流以释放与之相关的任何系统资源
            inputStream?.close()
            //这一行返回StringBuilder对象sb的内容作为字符串
            return sb.toString()

        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //总的来说，该方法读取由其ID以String形式指定的原始资源文件的内容。
        // 它使用BufferedReader从输入流中读取行，并将它们附加到StringBuilder中。最后，它以单个字符串的形式返回累积的行。
        return ""
    }
//    public static String getData(Context context, int id) {
//        InputStream inputStream = context.getResources().openRawResource(id);
//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//            inputStream.close();
//            return sb.toString();
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }


    data class PtNewsEntity(
        val image: Int,
        val title: String,
        val data : String,
        val content: String,
        val lookNumber : String,
        val likeNumber : String,
        val author : String
    ){}

    /**
     * 初始化入户走访信息
     */
    private fun initHot() {

        binding.PtHotRv.apply {
            layoutManager = GridLayoutManager(context, 2 , RecyclerView.VERTICAL, false)

            adapter = PtHotAdapter(
                ArrayList<PtHotEntity>().apply {
                    this.add(PtHotEntity(R.mipmap.k1,"汝南县板店乡：精准扶贫 入户走访暖人心"))
                    this.add(PtHotEntity(R.mipmap.g1,"精准扶贫零距离 入户走访暖人心"))
                },
                context,
                R.layout.re_item_poverty_hot
            )
        }

    }

    data class PtHotEntity(
        val image: Int,
        val title: String
    ){}

    /**
     * 初始化服务入口信息
     */
    private fun initMore() {
        val list : ArrayList<PtMoreEntity> = ArrayList<PtMoreEntity>()

        //TODO for取索引
        for ((index,data) in title_more.withIndex()) {
            list.add(PtMoreEntity(img_more[index], title_more[index]))
        }


        binding.ptMoreRv.apply {
            layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)

            adapter = PtMoreAdapter(
                list,
                context,
                R.layout.re_item_poverty_more
            )
        }
    }

    data class PtMoreEntity(
        var image : Int,
        var title : String
    ) {}

    /**
     * 初始化轮播图信息
     */
    private fun initBanner() {
        binding.povertyBanner.apply {
            setAdapter(object : BannerImageAdapter<Int>(img_banner){
                override fun onBindView(p0: BannerImageHolder?, p1: Int?, p2: Int, p3: Int) {
                    p0?.imageView?.setImageResource(p1!!)
                }
            })

            //设置指示小圆点
            indicator = CircleIndicator(context)
            //可直接设置轮播图的圆角
//            setBannerRound(100f)

            setOnBannerListener(object : OnBannerListener<Any> {

                override fun OnBannerClick(p0: Any?, p1: Int) {

                }
            })


        }
    }



}