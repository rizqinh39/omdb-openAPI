package com.rizqi.assesmentapp.ui.presenter

interface SearchPresenter {
    fun getSearchResult(paraM_KEY: String, query: String?, i: Int)
    fun onDestroy()
}