package com.rizqi.assesmentapp.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.gookios.customer.ui.adapter.SearchListAdapter
import com.rizqi.assesmentapp.R
import com.rizqi.assesmentapp.data.response.SearchResponse
import com.rizqi.assesmentapp.helper.AppConstant
import com.rizqi.assesmentapp.helper.listener.PaginationScrollListener
import com.rizqi.assesmentapp.helper.listener.RecyclerOnClickListener
import com.rizqi.assesmentapp.helper.util.CommonUtil
import com.rizqi.assesmentapp.ui.interactor.SearchPresenterImpl
import com.rizqi.assesmentapp.ui.presenter.SearchPresenter
import com.rizqi.assesmentapp.ui.view.SearchListView
import kotlinx.android.synthetic.main.fragment_movie.*
import org.jetbrains.anko.design.snackbar



class HomeFragment: BaseFragment(), SearchListView, RecyclerOnClickListener {


    private var listData: MutableList<SearchResponse.Search?>? = null
    private var mPresenter: SearchPresenter? = null
    private var mAdapter: SearchListAdapter? = null
    private var mIsLoading = true
    private var mQuerySearch = ""
    private var totalItemsCount: Int = 0
    private var mPageScroll = 1

    override val layoutResID: Int
        get() = R.layout.fragment_movie

    companion object {
        @JvmStatic
        val TAG = HomeFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
    }

    private fun initInstance() {
        listData = ArrayList()
        mPresenter = SearchPresenterImpl(context!!, this)
        mAdapter = SearchListAdapter(context!!, listData as MutableList<SearchResponse.Search?>)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setToolbar(R.id.toolbar_home, "Home", false)
        tv_no_data.text = getString(R.string.noresult)
        initRecycleview()
        initSearchView()
        initRefresh()
    }

    private fun initRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            mIsLoading = true
            refreshData()
            initView()
            getSearchList()
        }
    }

    private fun getSearchList() {
        swipeRefreshLayout.isRefreshing = true
        mPageScroll = 1
        totalItemsCount = 0
        mRecyclerViewOnScrollListener.setIsLoading(true)
        mPresenter?.getSearchResult(AppConstant.PARAM_KEY, mQuerySearch, mPageScroll)
    }

    private fun refreshData() {
        if (!listData.isNullOrEmpty()) {
            listData?.clear()
            mAdapter?.notifyDataSetChanged()
        }
    }

    private fun initRecycleview() {
        val layoutManager = LinearLayoutManager(context)
        rv_movie_list!!.setHasFixedSize(true)
        rv_movie_list.itemAnimator = DefaultItemAnimator()
        rv_movie_list.adapter = mAdapter
        mAdapter?.setOnClickListener(this)
        mRecyclerViewOnScrollListener.setThresholdCount(CommonUtil.threshold())
        mRecyclerViewOnScrollListener.setLayoutManager(layoutManager)
        rv_movie_list!!.layoutManager = layoutManager
        rv_movie_list.addOnScrollListener(mRecyclerViewOnScrollListener)
    }

    val mRecyclerViewOnScrollListener: PaginationScrollListener = object : PaginationScrollListener() {
        override fun loadMore(page: Int) {
            if (!CommonUtil.isConnected(context!!)) {
                view!!.snackbar("Check Your Connection")
                return
            }

            mIsLoading = false
            mAdapter?.setIsLoading(true)
            this.setIsLoading(true)
            mPageScroll = mPageScroll.inc()
            if(mPageScroll<=4) {
                mPresenter?.getSearchResult(AppConstant.PARAM_KEY, mQuerySearch, mPageScroll)
            }

        }

        override fun onPageUp() {
        }

        override fun onPageDown() {
        }

    }

    override fun onClick(view: View, position: Int) {
        val item = listData!![position]
        when (view.id) {
            R.id.tv_movie_detail -> {
                replaceFragment(false, R.id.container_home, SearchDetailFragment.newInstance(item!!.imdbID), SearchDetailFragment.TAG)
            }
        }
    }

    private fun initSearchView() {
        searchViewMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                tv_no_data.text = query
                mQuerySearch = query!!
                searchData(mQuerySearch)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mQuerySearch = newText!!
                searchData(mQuerySearch)
                return true
            }

        })
        searchViewMovie.setOnCloseListener(object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                swipeRefreshLayout.isRefreshing = false
                return false
            }

        })
    }

    private fun searchData(query: String?) {
        swipeRefreshLayout.isRefreshing = true
        mPageScroll = 1
        refreshData()
        if(query!!.isNotEmpty()) {
            tv_no_data.visibility =View.GONE
            mPresenter?.getSearchResult(AppConstant.PARAM_KEY, query, mPageScroll)
        }else{
            refreshData()
            swipeRefreshLayout.isRefreshing = false
            tv_no_data.visibility =View.VISIBLE
            tv_no_data.text = getString(R.string.noresult)
        }
    }

    override fun onDestroy() {
        mPresenter?.onDestroy()
        super.onDestroy()
    }

    override fun onSuccessSearchList(searchResponse: SearchResponse) {
       if(view!=null){
           swipeRefreshLayout.isRefreshing = false
           if (searchResponse.search!=null && searchResponse.search.isNotEmpty()) {
               tv_no_data.visibility = View.GONE
               mAdapter?.setIsLoading(false)
               appendUI(searchResponse.search)
               mRecyclerViewOnScrollListener.setIsLastPage(false)
               mRecyclerViewOnScrollListener.setIsLoading(false)
               totalItemsCount += searchResponse.search.size
               mRecyclerViewOnScrollListener.setPagination(totalItemsCount)
           }else if (listData!!.isEmpty() &&searchResponse.search==null){
               tv_no_data.visibility = View.VISIBLE
               tv_no_data.text = searchResponse.Error
           }
       }
    }

    private fun appendUI(search: List<SearchResponse.Search?>) {
        listData?.addAll(search)
        if (mIsLoading) {
            mAdapter?.notifyDataSetChanged()
        } else {
            mAdapter?.notifyItemChanged(listData?.size!!.plus(1), search.size)
        }
    }

    override fun onErrorSearchList(t: Throwable) {
        if (view!=null){
            view?.snackbar(t.localizedMessage)
        }
    }
}