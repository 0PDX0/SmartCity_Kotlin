package com.example.kotlin_zhcs.Base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<E> (
        private val list : MutableList<E>,
        @LayoutRes  //通过使用方法签名，可以确保仅接受布局资源标识符作为参数，并且有助于在编译过程中捕获潜在的错误或不匹配。@LayoutRes
        private val layoutId : Int
        ) : RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>() {

    //设置数据
    abstract fun setData(view : BaseViewHolder.ViewFind, data : E, position : Int, holder : BaseViewHolder)

    //填充数据
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (list.size >= 0){
            setData(holder.viewFind, list[position], position, holder)
        }
    }

    //列表数量
    override fun getItemCount(): Int {
        return list.size
    }

    //布局ID
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }


    /**
     * 封装ViewHolder
     * 这里把布局文件构造出来使用的View set进了这个viewFind中
     */
    class BaseViewHolder(private val view : View) : RecyclerView.ViewHolder(view){
        /*ViewFind是一个碎片类*/
        val viewFind = ViewFind().apply {
            this.setView(this@BaseViewHolder.view)  //这里必须要this@BaseViewHolder.view
        }

        /*kotlin中所有的内部类默认为静态的，这样很好的减少了内存泄漏问题。
        如果需要在内部类引用外部类的对象，可以使用inner声明内部类，使内部类变为非静态的，通过this@外部类名，指向外部类。*/
        inner class ViewFind : Fragment(){
            private lateinit var mView : View

            fun setView(view : View){
                mView = view
            }

            override fun getView(): View? {
                return mView
            }
        }
    }

}
















