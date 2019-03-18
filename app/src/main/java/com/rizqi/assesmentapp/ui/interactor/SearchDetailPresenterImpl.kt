package com.rizqi.assesmentapp.ui.interactor

import android.content.Context
import com.rizqi.assesmentapp.data.network.RestHelper
import com.rizqi.assesmentapp.data.response.MovieDetailResponse
import com.rizqi.assesmentapp.data.response.SearchResponse
import com.rizqi.assesmentapp.ui.presenter.SearchDetailPresenter
import com.rizqi.assesmentapp.ui.view.SearchDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchDetailPresenterImpl (context: Context, view: SearchDetailView): SearchDetailPresenter {

    private var mContext: Context = context
    private var mView: SearchDetailView = view
    private val mService = RestHelper.instance.service()
    private val compositeDisposable = CompositeDisposable()

    companion object {
        @JvmStatic
        val TAG: String = SearchPresenterImpl::class.java.simpleName
    }

    override fun getDetailMovies(paraM_KEY: String, idMovies: String?, plot: String) {
        val single: Disposable = mService.searchMoviesDetail(paraM_KEY, idMovies!!, plot)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : io.reactivex.functions.Consumer<MovieDetailResponse> {
                    override fun accept(searchResponse: MovieDetailResponse) {
                        mView.onSuccessMovieDetail(searchResponse)

                    }

                }, object : io.reactivex.functions.Consumer<Throwable> {
                    override fun accept(t: Throwable?) {
                        mView.onErrorMovieDetail(t!!)
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