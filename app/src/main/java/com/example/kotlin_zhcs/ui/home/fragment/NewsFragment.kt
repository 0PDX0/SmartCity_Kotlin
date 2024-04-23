package com.example.kotlin_zhcs.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_zhcs.R
import com.example.kotlin_zhcs.databinding.FragmentNews1Binding
import com.example.kotlin_zhcs.ui.home.HomeViewModel
import com.example.kotlin_zhcs.ui.home.adapter.NewsAdapter

class NewsFragment : Fragment() {

    lateinit var binding : FragmentNews1Binding

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_news1, container, false)

        binding = FragmentNews1Binding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNews(newsId)
    }


    private fun setNews(typeId : Int){
        viewModel.setNewsType(typeId.toString())
        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer { resources ->
            val body = resources.getOrNull()
            if (body != null){
                viewModel.newsList.clear()
                viewModel.newsList.addAll(body.rows!!)

                //适配器
                binding.newsRecycler.apply {
                    this.layoutManager = LinearLayoutManager(context)
                    this.adapter = NewsAdapter(viewModel.newsList, R.layout.re_item_news)
                }
            }
        })
    }


    companion object{
        var newsId = 0
    }
}



















