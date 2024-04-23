package com.example.kotlin_zhcs.ui.poverty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.kotlin_zhcs.Base.BaseRecyclerAdapter
import com.example.kotlin_zhcs.databinding.ReItemPovertyNewsBinding
import com.example.kotlin_zhcs.ui.poverty.PovertyFragment

class PtNewsAdapter(
    val list: MutableList<PovertyFragment.PtNewsEntity>,
    val image_list : MutableList<Int>,
    val context: Context,
    val layoutId : Int
) : BaseRecyclerAdapter<PovertyFragment.PtNewsEntity>(list, layoutId) {

    lateinit var binding : ReItemPovertyNewsBinding

    override fun setData(
        view: BaseViewHolder.ViewFind,
        data: PovertyFragment.PtNewsEntity,
        position: Int,
        holder: BaseViewHolder
    ) {

        Glide.with(context).load(image_list[position]).into(binding.ptNewsImage)

        binding.ptNewsTitle.text = data.title

        binding.ptNewsData.text = data.data

        binding.ptNewsLookNumber.text = data.lookNumber

        binding.ptNewsLikeNumber.text = data.likeNumber

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        binding = ReItemPovertyNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding.root)
    }

}