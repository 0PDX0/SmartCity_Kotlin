package com.example.kotlin_zhcs.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.kotlin_zhcs.App
import com.example.kotlin_zhcs.App.Companion.url
import com.example.kotlin_zhcs.Base.BaseRecyclerAdapter
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.databinding.ReItemMoreBinding
import com.example.kotlin_zhcs.login.model.MoreModel
import com.example.kotlin_zhcs.ui.AllService.DataAnalysis.DataAnalysisActivity
import com.example.kotlin_zhcs.ui.AllService.NumberBook.activity.NumberBookActivity
import com.example.kotlin_zhcs.ui.AllService.StopWhere.StopWhereActivity
import com.example.kotlin_zhcs.ui.AllService.YouthPost.YouthPostActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * 主页全部服务填充
 */
class MoreAdapter1(private val size : Int,
                  private val fr : Fragment,
                  private val list : MutableList<MoreModel.RowsBean>,
                  layoutId : Int) : BaseRecyclerAdapter<MoreModel.RowsBean>(list, layoutId) {    //BaseRecyclerAdapter：公共的继承类

    //直接对Xml的ID使用的插件,需设置这个
    //buildFeatures {
    //    viewBinding true
    //}
    lateinit var binding : ReItemMoreBinding

    override fun setData(
        view: BaseViewHolder.ViewFind,
        data: MoreModel.RowsBean,
        position: Int,
        holder: BaseViewHolder
    ) {

        //添加动画
        holder.itemView.animation = App.getAnimationTopAndBottom()

        //对所有服务进行排序(从高到低)
//        list.sortByDescending { it.id }   //被他害了，服务搜索两个选项会变为一个重复的

        binding.moreTitle.text = data.serviceName

        Glide.with(fr.requireContext()).load(url + data.imgUrl)
            .error(R.mipmap.ic_launcher).into(binding.moreImage)

        val con = binding.root.context

        val title : String = binding.moreTitle.text.toString()

        holder.itemView.setOnClickListener {
            if (title == "全部服务"){
                val nav = fr.requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
                nav.selectedItemId = R.id.navigation_dashboard
            }

            if (title == "停哪儿"){
                con?.startActivity(Intent(con, StopWhereActivity::class.java))
            }

            if (title == "数字图书馆"){
                con?.startActivity(Intent(con, NumberBookActivity::class.java))
            }

            if (title == "数据分析"){
                con?.startActivity(Intent(con, DataAnalysisActivity::class.java))
            }

            if (title == "青年驿站"){
                con?.startActivity(Intent(con, YouthPostActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        binding = ReItemMoreBinding.inflate(LayoutInflater.from(fr.context), parent, false)
        return BaseViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return size
    }

}













/*if (d.moreUrl.isEmpty()){
            holder.viewFind.view?.findViewById<ImageView>(R.id.moreImage)?.setImageResource(R.mipmap.ic_launcher_round)
            holder.viewFind.view?.findViewById<TextView>(R.id.moreTitle)?.text = d.moreTitle
        }else{
            glide(d.moreUrl, holder.itemView.findViewById(R.id.moreImage))
            holder.viewFind.view?.findViewById<TextView>(R.id.moreTitle)?.text = d.moreTitle
        }

        if (SS){
            if (position == 9){
                holder.viewFind.view?.findViewById<ImageView>(R.id.moreImage)?.setImageResource(R.mipmap.ic_launcher_round)
                holder.viewFind.view?.findViewById<TextView>(R.id.moreTitle)?.text = "全部服务"
            }
        }*/





