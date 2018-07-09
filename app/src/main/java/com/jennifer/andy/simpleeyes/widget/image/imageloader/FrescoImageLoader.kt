package com.jennifer.andy.simpleeyes.widget.image.imageloader

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView
import com.youth.banner.loader.ImageLoader


/**
 * Author:  andy.xwt
 * Date:    2017/11/3 11:58
 * Description:banner中的图片加载器
 */

class FrescoImageLoader : ImageLoader() {

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        imageView?.setImageURI(Uri.parse(path as String))
    }


    override fun createImageView(context: Context?): ImageView {
        return SimpleDraweeView(context)
    }
}