package com.example.kotlin_zhcs.ui.poverty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.kotlin_zhcs.Base.BaseRecyclerAdapter
import com.example.kotlin_zhcs.databinding.ReItemPovertyHotBinding
import com.example.kotlin_zhcs.ui.poverty.PovertyFragment

class PtHotAdapter(
    list: MutableList<PovertyFragment.PtHotEntity>,
    val context: Context,
    val layoutId : Int
) : BaseRecyclerAdapter<PovertyFragment.PtHotEntity>(list, layoutId) {

    lateinit var binding : ReItemPovertyHotBinding

    override fun setData(
        view: BaseViewHolder.ViewFind,
        data: PovertyFragment.PtHotEntity,
        position: Int,
        holder: BaseViewHolder
    ) {

        Glide.with(context).load(data.image).into(binding.povertyHotImage)
        binding.povertyHotTitle.text = data.title

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        binding = ReItemPovertyHotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding.root)
    }

}