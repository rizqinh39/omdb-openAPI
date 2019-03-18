package com.rizqi.assesmentapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rizqi.assesmentapp.R
import com.rizqi.assesmentapp.data.response.MovieDetailResponse
import com.rizqi.assesmentapp.helper.AppConstant
import com.rizqi.assesmentapp.helper.GlideHelper
import com.rizqi.assesmentapp.ui.interactor.SearchDetailPresenterImpl
import com.rizqi.assesmentapp.ui.presenter.SearchDetailPresenter
import com.rizqi.assesmentapp.ui.view.SearchDetailView
import kotlinx.android.synthetic.main.fragment_searchdetail.*
import org.jetbrains.anko.design.snackbar

class SearchDetailFragment: BaseFragment(), SearchDetailView {


    private var idMovies: String? = null
    private var mPresenter: SearchDetailPresenter? = null
    private  var mData: MovieDetailResponse? = null

    override val layoutResID: Int
        get() = R.layout.fragment_searchdetail

    companion object {
        @JvmStatic
        val TAG = SearchDetailFragment::class.java.simpleName
        const val TAG_EXTRA = "extra_data"

        @JvmStatic
        fun newInstance(imdbID: String?): SearchDetailFragment {
            val dialog = SearchDetailFragment()
            val bundle = Bundle()
            bundle.putString(TAG_EXTRA, imdbID)
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
    }

    private fun initInstance() {
        mPresenter = SearchDetailPresenterImpl(context!!, this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleData()
        getDataDetail()
    }

    private fun getDataDetail() {
        mPresenter?.getDetailMovies(AppConstant.PARAM_KEY, idMovies, "full")
    }

    private fun getBundleData() {
        if (this.arguments != null) {
            idMovies = arguments?.getString(TAG_EXTRA)
        }
    }

    private fun initView() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar_home_detail)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_home_detail.setNavigationOnClickListener { fragmentManager?.popBackStack() }
        tv_title_toolbar.text = "Detail"
        GlideHelper.loadImageCenterCropFlat(context!!, mData?.poster!!, iv_header_home_detail)
        ct_search_detail.isTitleEnabled = false
        tv_detail_title.text = mData?.title
        tv_detail_plot.text = mData?.plot
        tv_detail_genre.text = mData?.genre
        tv_detail_years.text = mData?.year
        tv_detail_duration.text = "- "+mData?.runtime
        tv_detail_rating.text = mData?.imdbRating.toString()
    }

    override fun onDestroy() {
        mPresenter?.onDestroy()
        super.onDestroy()
    }

    override fun onSuccessMovieDetail(searchResponse: MovieDetailResponse) {
        if (view!=null){
            mData = searchResponse
            initView()
        }
    }

    override fun onErrorMovieDetail(t: Throwable) {
       if (view!=null){
           view?.snackbar(t.localizedMessage)
       }
    }
}