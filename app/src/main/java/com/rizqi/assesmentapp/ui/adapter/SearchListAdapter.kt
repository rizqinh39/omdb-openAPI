package com.gookios.customer.ui.adapter

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rizqi.assesmentapp.R
import com.rizqi.assesmentapp.data.response.SearchResponse
import com.rizqi.assesmentapp.helper.GlideHelper
import com.rizqi.assesmentapp.helper.listener.RecyclerOnClickListener
import com.rizqi.assesmentapp.ui.adapter.BaseViewHolder
import kotlinx.android.synthetic.main.item_search_list.view.*


class SearchListAdapter(mContext: Context, listMenu: MutableList<SearchResponse.Search?>) :
        RecyclerView.Adapter<BaseViewHolder>() {

    val TAG = SearchListAdapter::class.java.simpleName
    private var context: Context? = null
    private var mItems: MutableList<SearchResponse.Search?>? = null
    private var listener: RecyclerOnClickListener? = null

    private var isLoading = false
    private var loadingViewPosition: Int = 0

    init {
        context = mContext
        mItems = listMenu
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_list, parent, false)
        val viewHolder = BaseViewHolder(view)
        //viewHolder.setOnClickListener(setOnClickListener())
        return viewHolder
    }

    override fun getItemCount(): Int = if (mItems != null) mItems!!.size else 0

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        if (isLoading) {
            if (itemCount > loadingViewPosition && mItems?.get(loadingViewPosition) == null) {
                mItems?.removeAt(loadingViewPosition)
                notifyItemRemoved(loadingViewPosition)
            }

            Handler().post(Runnable {
                try {
                    if (itemCount > 0 && mItems?.get(itemCount - 1) != null) {
                        mItems?.add(null)
                        loadingViewPosition = itemCount - 1
                        notifyItemInserted(loadingViewPosition)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "setIsLoading", e)
                }
            })
        } else {
            try {
                if (itemCount > loadingViewPosition && mItems?.get(loadingViewPosition) == null) {
                    mItems?.removeAt(loadingViewPosition)
                    notifyItemRemoved(loadingViewPosition)
                }
            } catch (e: Exception) {
                Log.e(TAG, "setIsLoading", e)
            }

        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = mItems?.get(position)
        if (item != null) {
            GlideHelper.loadImageCenterCrop(context!!, item.poster!!, holder.itemView.iv_movie_title)
            holder.itemView.tv_movie_title.text = item.title
            holder.itemView.tv_movie_year.text = item.year
            holder.itemView.tv_movie_genre.text = item.type
            holder.itemView.tv_movie_detail.tag = R.id.tv_movie_detail
            holder.itemView.tv_movie_detail.setOnClickListener(clickListener)
            holder.itemView.tv_movie_detail.setTag(R.id.tv_movie_detail, holder)
            holder.itemView.setTag(R.id.cl_item_search, holder)
        }
    }

    fun setOnClickListener(listener: RecyclerOnClickListener) {
        this.listener = listener
    }

    private val clickListener = View.OnClickListener { v ->
        if (listener != null) {
            if (v.id == v.tag) {
                val vh = v.getTag(v.id) as androidx.recyclerview.widget.RecyclerView.ViewHolder
                val position = vh.adapterPosition
                listener!!.onClick(v, position)
            }
        }
    }

}