package com.savvy.app.news_detail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.savvy.app.R
import com.savvy.app.base.BaseFragment
import com.savvy.app.base.extension.displayDateTime
import com.savvy.app.base.extension.loadUrl
import com.savvy.app.databinding.FragmentNewsDetailBinding
import com.savvy.core.base.delegate.viewBinding
import com.savvy.data.base.model.news.News
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : BaseFragment(R.layout.fragment_news_detail) {
    companion object {

        @JvmStatic
        private val TAG = NewsDetailFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): NewsDetailFragment {
            val fragment = NewsDetailFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    // Data Members
    private val mBinding by viewBinding(FragmentNewsDetailBinding::bind)
    private val mViewModel by viewModels<NewsDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        arguments?.apply { }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initInstance(view, savedInstanceState)
        val newsDetail = mViewModel.loadNewsDetail()
        updateDataToView(context, newsDetail)
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateDataToView(context: Context, news: News){
        with(mBinding){
            textTitle.text = news.title
            textContent.text = news.content
            imageNews.loadUrl(context, news.urlToImage)
            textUpdatedTime.text = context.getString(R.string.updated_time, news.publishedAt.displayDateTime())
        }
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        // Init View instance
        with(mBinding) {
            appbar.textViewBack.isVisible = true
            appbar.textViewBack.setOnClickListener {
                findNavController().popBackStack()
            }
            appbar.apply {
                toolbar.setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
                textViewTitle.setText(R.string.details)
            }
        }
    }

    override val screenName: String
        get() = TAG
}
