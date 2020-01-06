package com.jennifer.andy.simpleeyes.ui.search.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.Content
import com.jennifer.andy.simpleeyes.ui.video.VideoDetailActivity
import com.jennifer.andy.base.utils.getElapseTimeForShow
import com.jennifer.andy.simpleeyes.widget.ItemHeaderView
import java.util.*


/**
 * Author:  andy.xwt
 * Date:    2018/4/9 10:19
 * Description:
 */

class SearchVideoAdapter(data: List<Content>?) : BaseQuickAdapter<Content, BaseViewHolder>(data) {

    /**
     * 卡片类型
     */
    companion object {

        const val VIDEO_COLLECTION_WITH_BRIEF_TYPE = 0
        const val VIDEO_TYPE = 1

        const val VIDEO_COLLECTION_WITH_BRIEF = "videoCollectionWithBrief"
        const val VIDEO = "video"
    }


    init {
        multiTypeDelegate = object : MultiTypeDelegate<Content>() {
            override fun getItemType(content: Content?): Int {
                when (content?.type) {
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

    override fun convert(helper: BaseViewHolder?, content: Content) {
        when (helper?.itemViewType) {
            VIDEO_COLLECTION_WITH_BRIEF_TYPE -> setCollectionBriefInfo(helper, content)
            VIDEO_TYPE -> setSingleVideoInfo(helper, content)
        }
    }

    /**
     * 设置带关注人的视频集合信息
     */
    private fun setCollectionBriefInfo(helper: BaseViewHolder, content: Content) {
        //设置视频集合信息
        with(helper) {
            getView<RecyclerView>(R.id.rv_recycler).apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                adapter = CollectionBriefAdapter(content.data.itemList).apply {
                    onItemClickListener = OnItemClickListener { _, _, position ->
                        //跳转到播放视频详情
                        val item = getItem(position)
                        VideoDetailActivity.start(mContext, item!!.data, data as ArrayList<Content>, position)

                    }
                }

            }
            //设置头布局
            content.data.header?.let { getView<ItemHeaderView>(R.id.item_header_view).setHeader(it, content.data.dataType) }

        }

    }

    /**
     * 设置单视频信息
     */
    private fun setSingleVideoInfo(helper: BaseViewHolder, item: Content) {
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_image)
        imageView.setImageURI(item.data.cover.feed)
        helper.setText(R.id.tv_single_title, item.data.title)
        val description = "#${item.data.category}   /   ${getElapseTimeForShow(item.data.duration)}"
        helper.setText(R.id.tv_single_desc, description)
    }


}