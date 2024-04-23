package com.example.kotlin_zhcs.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kotlin_zhcs.Base.BaseRecyclerAdapter
import com.example.kotlin_zhcs.databinding.ItemNewsCommentBinding
import com.example.kotlin_zhcs.login.model.NewsComment

class NewsCommentAdapter(
    var list : MutableList<NewsComment.RowsBean>,
    var context: Context,
    var layoutId : Int
) : BaseRecyclerAdapter<NewsComment.RowsBean>(list, layoutId) {

    lateinit var binding : ItemNewsCommentBinding

    override fun setData(
        view: BaseViewHolder.ViewFind,
        data: NewsComment.RowsBean,
        position: Int,
        holder: BaseViewHolder
    ) {

        binding.userName.text = data.userName

        binding.commentDate.text = data.commentDate

        binding.commentContent.text = data.content

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        binding = ItemNewsCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding.root)
    }

}