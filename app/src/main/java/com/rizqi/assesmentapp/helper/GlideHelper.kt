package com.rizqi.assesmentapp.helper

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


object GlideHelper {


    fun loadImageCenterCrop(context: Context, url: String, resId: ImageView) {

        Glide.with(context)
                .load(url)
                .apply(RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE))
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(46, 0, RoundedCornersTransformation.CornerType.ALL)))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .into(resId)
    }

    fun loadImageCenterCropFlat(context: Context, url: String, resId: ImageView) {

        val ro = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)

        Glide.with(context)
                .load(url)
                .apply(ro)
                .into(resId)
    }

}
