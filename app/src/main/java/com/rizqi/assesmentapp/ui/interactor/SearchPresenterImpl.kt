package com.rizqi.assesmentapp.ui.interactor

import android.content.Context
import com.rizqi.assesmentapp.data.network.RestHelper
import com.rizqi.assesmentapp.data.response.SearchResponse
import com.rizqi.assesmentapp.ui.presenter.SearchPresenter
import com.rizqi.assesmentapp.ui.view.SearchListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchPresenterImpl(context: Context, view: SearchListView): SearchPresenter {

    private var mContext: Context = context
    private var mView: SearchListView = view
    private val mService = RestHelper.instance.service()
    private val compositeDisposable = CompositeDisposable()

    companion object {
        @JvmStatic
        val TAG: String = SearchPresenterImpl::class.java.simpleName
    }

    override fun getSearchResult(paraM_KEY: String, query: String?, i: Int) {
        val single: Disposable = mService.searchMovies(paraM_KEY, query!!, i)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.functions.Consumer<SearchResponse> {
                    override fun accept(searchResponse: SearchResponse) {
                            mView.onSuccessSearchList(searchResponse)

                    }

                }, object : io.reactivex.functions.Consumer<Throwable> {
                    override fun accept(t: Throwable?) {
                        mView.onErrorSearchList(t!!)
                    }

                })
        compositeDisposable.add(single)
    }

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        compositeDisposable.clear()
    }
}