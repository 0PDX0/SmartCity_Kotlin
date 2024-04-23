package com.example.kotlin_zhcs.ui.poverty.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.kotlin_zhcs.Base.BaseRecyclerAdapter
import com.example.kotlin_zhcs.databinding.ReItemPovertyMoreBinding
import com.example.kotlin_zhcs.ui.poverty.PovertyFragment

class PtMoreAdapter(
    val list : MutableList<PovertyFragment.PtMoreEntity>,
    val context : Context,
    val layoutId : Int
) : BaseRecyclerAdapter<PovertyFragment.PtMoreEntity>(list, layoutId) {

    lateinit var binding : ReItemPovertyMoreBinding

    override fun setData(
        view: BaseViewHolder.ViewFind,
        data: PovertyFragment.PtMoreEntity,
        position: Int,
        holder: BaseViewHolder
    ) {

        Glide.with(context).load(data.image).into(binding.ptMoreImage)
        binding.ptMoreTitle.text = data.title

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        binding = ReItemPovertyMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding.root)
    }

}