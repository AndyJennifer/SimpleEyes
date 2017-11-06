package com.jennifer.andy.simpleeyes.ui.category.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.facebook.drawee.view.SimpleDraweeView
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.entity.AndyInfo
import com.jennifer.andy.simpleeyes.image.FrescoImageLoader
import com.jennifer.andy.simpleeyes.widget.CardNormalBottom
import com.jennifer.andy.simpleeyes.widget.EliteImageView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig


/**
 * Author:  andy.xwt
 * Date:    2017/10/27 16:35
 * Description: 主页信息适配器
 */

class CategoryAdapter(data: MutableList<AndyInfo.ItemListBeanX>) : BaseQuickAdapter<AndyInfo.ItemListBeanX, BaseViewHolder>(data) {

    /**
     * 卡片类型
     */
    companion object {

        private val VIDEO_BANNER_TYPE = 0
        private val VIDEO_FOLLOW_CARD_TYPE = 1
        private val VIDEO_HORIZONTAL_SCROLL_CARD_TYPE = 2
        private val VIDEO_COLLECTION_WITH_COVER_TYPE = 3
        private val VIDEO_SQUARE_CARD_COLLECTION_TYPE = 4

        private val VIDEO_BANNER = "banner"
        private val VIDEO_FOLLOW_CARD = "followCard"
        private val VIDEO_HORIZONTAL_CARD = "horizontalScrollCard"
        private val VIDEO_COLLECTION_WITH_COVER = "videoCollectionWithCover"
        private val VIDEO_SQUARE_CARD_COLLECTION = "squareCardCollection"
    }


    init {
        multiTypeDelegate = object : MultiTypeDelegate<AndyInfo.ItemListBeanX>() {
            override fun getItemType(andyInfoItem: AndyInfo.ItemListBeanX?): Int {
                when (andyInfoItem?.type) {
                    VIDEO_BANNER -> return VIDEO_BANNER_TYPE
                    VIDEO_FOLLOW_CARD -> return VIDEO_FOLLOW_CARD_TYPE
                    VIDEO_HORIZONTAL_CARD -> return VIDEO_HORIZONTAL_SCROLL_CARD_TYPE
                    VIDEO_COLLECTION_WITH_COVER -> return VIDEO_COLLECTION_WITH_COVER_TYPE
                    VIDEO_SQUARE_CARD_COLLECTION -> return VIDEO_SQUARE_CARD_COLLECTION_TYPE
                }
                return VIDEO_FOLLOW_CARD_TYPE
            }
        }
        multiTypeDelegate.registerItemType(VIDEO_BANNER_TYPE, R.layout.layout_card_banner)
        multiTypeDelegate.registerItemType(VIDEO_FOLLOW_CARD_TYPE, R.layout.layout_follow_card)
        multiTypeDelegate.registerItemType(VIDEO_HORIZONTAL_SCROLL_CARD_TYPE, R.layout.layout_horizontal_scroll_card)
        multiTypeDelegate.registerItemType(VIDEO_COLLECTION_WITH_COVER_TYPE, R.layout.layout_collection_with_cover)
        multiTypeDelegate.registerItemType(VIDEO_SQUARE_CARD_COLLECTION_TYPE, R.layout.layout_square_collection)
    }

    override fun convert(helper: BaseViewHolder?, item: AndyInfo.ItemListBeanX) {
        when (helper?.itemViewType) {
            VIDEO_BANNER_TYPE -> setBannerInfo(helper, item)
            VIDEO_FOLLOW_CARD_TYPE -> setFollowCardInfo(helper, item)
            VIDEO_HORIZONTAL_SCROLL_CARD_TYPE -> setHorizontalCardInfo(helper, item.data.itemList)
            VIDEO_COLLECTION_WITH_COVER_TYPE -> setCollectionCardWithCoverInfo(helper, item.data)
            VIDEO_SQUARE_CARD_COLLECTION_TYPE -> setSquareCollectionInfo(helper, item.data.itemList)
        }
    }


    /**
     * 设置视频banner信息
     */
    private fun setBannerInfo(helper: BaseViewHolder, item: AndyInfo.ItemListBeanX) {
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_image)
        imageView.setImageURI(item.data.content.data.cover.feed)
    }

    /**
     * 设置单视频卡片信息
     */
    private fun setFollowCardInfo(helper: BaseViewHolder, item: AndyInfo.ItemListBeanX) {
        val info = item.data
        val cardNormalBottom = helper.getView<CardNormalBottom>(R.id.card_bottom)
        cardNormalBottom.setTitle(info.header.title)
        cardNormalBottom.setDescription(info.header.description)
        cardNormalBottom.setIconUrl(info.header.icon)
        cardNormalBottom.setIconType(info.header.iconType == "round")//设置图标形状
        //todo 设置翻译
        val eliteView = helper.getView<EliteImageView>(R.id.elite_view)
        eliteView.setImageUrl(info.content.data.cover.feed)
        eliteView.setDailyVisible(info.content.data.library == "DAILY")

    }

    /**
     * 设置水平卡片信息
     */
    private fun setHorizontalCardInfo(helper: BaseViewHolder, itemList: MutableList<AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData>) {
        val banner = helper.getView<Banner>(R.id.banner)
        banner.setImageLoader(FrescoImageLoader())
        banner.setImages(getHorizonTalCardUrl(itemList))
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner.setIndicatorGravity(BannerConfig.CENTER)
        banner.isAutoPlay(true)
        banner.start()
        banner.setDelayTime(5000)
        banner.setOnBannerListener {
            //todo 点击跳转
        }
    }

    /**
     * 获取水平卡片图片地址
     */
    private fun getHorizonTalCardUrl(itemList: MutableList<AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData>) = itemList.map { it.data.image }


    /**
     * 设置集合卡片信息
     */
    private fun setCollectionCardWithCoverInfo(helper: BaseViewHolder, item: AndyInfo.ItemListBeanX.DataBeanXXX) {
        val eliteImageView = helper.getView<EliteImageView>(R.id.iv_image)
        val collectionRecycler = helper.getView<RecyclerView>(R.id.rv_collection_cover_recycler)
        val collectionCardCoverAdapter = CollectionCardCoverAdapter(item.itemList)
        collectionRecycler.isNestedScrollingEnabled = false
        collectionRecycler.recycledViewPool = recyclerView.recycledViewPool
        collectionRecycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        collectionRecycler.adapter = collectionCardCoverAdapter
        eliteImageView.setImageUrl(item.header.cover)
        eliteImageView.setArrowVisible(true)

    }

    /**
     * 设置矩形卡片信息
     */
    private fun setSquareCollectionInfo(helper: BaseViewHolder, itemList: MutableList<AndyInfo.ItemListBeanX.DataBeanXXX.ItemListData>) {
        val squareRecycler = helper.getView<RecyclerView>(R.id.rv_square_recycler)
        val showAllContainer = helper.getView<RelativeLayout>(R.id.ll_more_container)
        squareRecycler.isNestedScrollingEnabled = false
        squareRecycler.recycledViewPool = recyclerView.recycledViewPool //使用同一个缓存池
        val squareCollectionAdapter = SquareCollectionAdapter(itemList)
        squareRecycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        squareRecycler.adapter = squareCollectionAdapter

        squareCollectionAdapter.onItemClickListener = OnItemClickListener { _, _, position ->
            //todo 跳转到分类详情
        }
        showAllContainer.setOnClickListener {
            //todo 查看更多
        }
    }
}