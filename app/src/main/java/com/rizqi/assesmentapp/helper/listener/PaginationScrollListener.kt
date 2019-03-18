package com.rizqi.assesmentapp.helper.listener

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class PaginationScrollListener : RecyclerView.OnScrollListener() {

    companion object {
        private val TAG = PaginationScrollListener::class.java.simpleName
    }

    private var mGridLayoutManager: GridLayoutManager? = null
    private var mStaggeredGridLayoutManager: StaggeredGridLayoutManager? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null

    private var isLoading = false
    private var isLastPage = false

    var mThreshold = 10

    private var y: Int = 0

    fun setThresholdCount(threshold: Int) {
        mThreshold = threshold
    }

    fun setLayoutManager(manager: GridLayoutManager) {
        mGridLayoutManager = manager
    }

    fun setLayoutManager(manager: StaggeredGridLayoutManager) {
        mStaggeredGridLayoutManager = manager
    }

    fun setLayoutManager(manager: LinearLayoutManager) {
        mLinearLayoutManager = manager
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    fun setIsLastPage(isLastPage: Boolean) {
        this.isLastPage = isLastPage
    }

    fun setPagination(pagination: Int) {
        if (pagination > mThreshold)
            this.mThreshold = pagination
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        this.y = dy
        var visibleItemCount = -1
        var totalItemCount = -1
        var firstVisibleItemPosition = -1

        if (mGridLayoutManager != null) {
            visibleItemCount = mGridLayoutManager!!.childCount
            totalItemCount = mGridLayoutManager!!.itemCount
            firstVisibleItemPosition = mGridLayoutManager!!.findFirstVisibleItemPosition()
        } else if (mLinearLayoutManager != null) {
            visibleItemCount = mLinearLayoutManager!!.childCount
            totalItemCount = mLinearLayoutManager!!.itemCount
            firstVisibleItemPosition = mLinearLayoutManager!!.findFirstVisibleItemPosition()
        }

        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= mThreshold) {
                isLoading = true
                isLastPage = true
                loadMore(mThreshold)
            }
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (RecyclerView.SCROLL_STATE_IDLE == newState) {
            if (y <= 0) {
                onPageUp()
            } else {
                onPageDown()
                y = 0
            }
        }
    }

    abstract fun loadMore(page: Int)

    abstract fun onPageUp()

    abstract fun onPageDown()

}
