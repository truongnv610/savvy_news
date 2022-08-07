package com.savvy.app.news

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import dagger.hilt.android.AndroidEntryPoint
import com.savvy.core.base.delegate.viewBinding
import com.savvy.app.R
import com.savvy.app.base.BaseFragment
import com.savvy.app.databinding.FragmentNewsListBinding
import com.savvy.app.di.LayoutManagerModule
import com.savvy.app.news.controllers.NewsListPageController
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

@AndroidEntryPoint
class FragmentNewsList : BaseFragment(R.layout.fragment_news_list) {
    companion object {

        @JvmStatic
        private val TAG = FragmentNewsList::class.java.simpleName

        @JvmStatic
        fun newInstance(): FragmentNewsList {
            val fragment = FragmentNewsList()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    // Injection
    @Inject
    lateinit var mController: NewsListPageController

    @Inject
    @Named(LayoutManagerModule.VERTICAL_LINEAR_LAYOUT_MANAGER)
    lateinit var mLayoutManagerProvider: Provider<LinearLayoutManager>

    // Data Members
    private val mBinding by viewBinding(FragmentNewsListBinding::bind)
    private val mViewModel by viewModels<NewsListViewModel>()
    private lateinit var mLinearLayoutManager: LinearLayoutManager

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

        mViewModel.loadNewsListData()

        mViewModel.newsPagingDataLiveData.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                mBinding.srlNews.isRefreshing = false
                mController.submitData(it)
            }
        }
    }

    private fun initInstance(rootView: View?, savedInstanceState: Bundle?) {
        // Init View instance
        mLinearLayoutManager = mLayoutManagerProvider.get()
        with(mBinding) {
            appbar.viewSearchBar.layoutSearch.isVisible = true
            recyclerView.apply {
                layoutManager = mLinearLayoutManager
                adapter = mController.adapter
            }

            appbar.apply {
                toolbar.navigationIcon = null
                textViewTitle.setText(R.string.news)
            }

            srlNews.setOnRefreshListener {
                mBinding.srlNews.isRefreshing = true
                mViewModel.loadNewsListData(appbar.viewSearchBar.editText.text.toString())
            }

            subscriptions += mController.bindSelectedNews()
                .throttleLast(300, TimeUnit.MILLISECONDS)
                .observeOn(schedulersFacade.ui)
                .subscribeBy(
                    onError = Timber::e,
                    onNext = { news ->
                        findNavController().navigate(
                            FragmentNewsListDirections.actionFragmentNewsListToFragmentNewsDetail(news)
                        )
                    }
                )

            subscriptions += appbar.viewSearchBar.editText.afterTextChangeEvents()
                .map { it.editable?.toString() ?: "" }
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(schedulersFacade.ui)
                .subscribeBy(
                    onError = Timber::e,
                    onNext = {
                        mViewModel.loadNewsListData(it)
                    }
                )
        }
    }

    override val screenName: String
        get() = TAG
}
