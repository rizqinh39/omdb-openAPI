package com.rizqi.assesmentapp.ui.presenter

interface SearchDetailPresenter {
    fun getDetailMovies(paraM_KEY: String, idMovies: String?, plot: String)
    fun onDestroy()
}