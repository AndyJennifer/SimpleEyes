package com.jennifer.andy.simpleeyes.ui.search.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.ItemList
import com.jennifer.andy.simpleeyes.utils.TimeUtils


/**
 * Author:  andy.xwt
 * Date:    2018/4/9 10:19
 * Description:
 */

class SearchVideoAdapter(data: MutableList<ItemList>?) : BaseQuickAdapter<ItemList, BaseViewHolder>(data) {

    /**
     * 卡片类型
     */
    val VIDEO_COLLECTION_WITH_BRIEF_TYPE = 0
    val VIDEO_TYPE = 1

    val VIDEO_COLLECTION_WITH_BRIEF = "videoCollectionWithBrief"
    val VIDEO = "video"


    init {
        multiTypeDelegate = object : MultiTypeDelegate<ItemList>() {
            override fun getItemType(andyInfoItem: ItemList?): Int {
                when (andyInfoItem?.type) {
                    VIDEO_COLLECTION_WITH_BRIEF -> return VIDEO_COLLECTION_WITH_BRIEF_TYPE
                    VIDEO -> return VIDEO_TYPE
                }
                return VIDEO_TYPE
            }
        }
        with(multiTypeDelegate) {
            registerItemType(VIDEO_COLLECTION_WITH_BRIEF_TYPE, R.layout.layout_collection_with_brief)
            registerItemType(VIDEO_TYPE, R.layout.layout_single_video)
        }

    }

    override fun convert(helper: BaseViewHolder?, item: ItemList) {
        when (helper?.itemViewType) {
            VIDEO_COLLECTION_WITH_BRIEF_TYPE -> setCollectionBriefInfo(helper, item)
            VIDEO_TYPE -> setSingleVideoInfo(helper, item)
        }
    }

    /**
     * 设置带关注人的视频集合信息
     */
    private fun setCollectionBriefInfo(helper: BaseViewHolder, item: ItemList) {
        //设置视频集合信息
        val recyclerView = helper.getView<RecyclerView>(R.id.rv_recycler)
        recyclerView.isNestedScrollingEnabled = false
        val collectionBriefAdapter = CollectionBriefAdapter(item.data.itemList)
        recyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = collectionBriefAdapter
        collectionBriefAdapter.onItemClickListener = OnItemClickListener { _, _, position ->
            //跳转到播放视频详情

        }
        //设置作者信息
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_head)
        if (item.data.header.iconType == "round") {//判断头像类型
            imageView.hierarchy.roundingParams = RoundingParams.asCircle()
        } else {
            imageView.hierarchy.roundingParams?.roundAsCircle = false
        }
        imageView.setImageURI(item.data.header.icon)
        helper.setText(R.id.tv_title, item.data.header.title)
        helper.setText(R.id.tv_desc, item.data.header.description)


    }

    /**
     * 设置单视频信息
     */
    private fun setSingleVideoInfo(helper: BaseViewHolder, item: ItemList) {
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_image)
        imageView.setImageURI(item.data.cover.feed)
        helper.setText(R.id.tv_single_title, item.data.title)
        val description = "#${item.data.category}   /   ${TimeUtils.getElapseTimeForShow(item.data.duration)}"
        helper.setText(R.id.tv_single_desc, description)
    }


}