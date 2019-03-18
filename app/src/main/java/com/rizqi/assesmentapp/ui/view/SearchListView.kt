package com.rizqi.assesmentapp.ui.view

import com.rizqi.assesmentapp.data.response.SearchResponse

interface SearchListView {
    fun onSuccessSearchList(searchResponse: SearchResponse)
    fun onErrorSearchList(t: Throwable)
}