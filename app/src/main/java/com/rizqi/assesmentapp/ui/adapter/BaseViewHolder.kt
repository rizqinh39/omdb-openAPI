package com.rizqi.assesmentapp.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rizqi.assesmentapp.helper.listener.RecyclerOnClickListener


class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var onClickListener: RecyclerOnClickListener? = null

    init {
        itemView.setOnClickListener(this)
    }

    fun setOnClickListener(listener: RecyclerOnClickListener) {
        this.onClickListener = listener
    }

    override fun onClick(p0: View?) {
        onClickListener?.onClick(p0!!, adapterPosition)
    }

}