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
import com.jennifer.andy.simpleeyes.entity.ItemBean
import com.jennifer.andy.simpleeyes.entity.ItemList
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

class CategoryAdapter(data: MutableList<ItemList>) : BaseQuickAdapter<ItemList, BaseViewHolder>(data) {

    /**
     * 卡片类型
     */
    val VIDEO_BANNER_TYPE = 0
    val VIDEO_FOLLOW_CARD_TYPE = 1
    val VIDEO_HORIZONTAL_SCROLL_CARD_TYPE = 2
    val VIDEO_COLLECTION_WITH_COVER_TYPE = 3
    val VIDEO_SQUARE_CARD_COLLECTION_TYPE = 4
    val VIDEO_BANNER_THREE_TYPE = 8

    val VIDEO_BANNER = "banner"
    val VIDEO_FOLLOW_CARD = "followCard"
    val VIDEO_HORIZONTAL_CARD = "horizontalScrollCard"
    val VIDEO_COLLECTION_WITH_COVER = "videoCollectionWithCover"
    val VIDEO_SQUARE_CARD_COLLECTION = "squareCardCollection"
    val VIDEO_BANNER_THREE = "banner3"


    init {
        multiTypeDelegate = object : MultiTypeDelegate<ItemList>() {
            override fun getItemType(andyInfoItem: ItemList?): Int {
                when (andyInfoItem?.type) {
                    VIDEO_BANNER -> return VIDEO_BANNER_TYPE
                    VIDEO_FOLLOW_CARD -> return VIDEO_FOLLOW_CARD_TYPE
                    VIDEO_HORIZONTAL_CARD -> return VIDEO_HORIZONTAL_SCROLL_CARD_TYPE
                    VIDEO_COLLECTION_WITH_COVER -> return VIDEO_COLLECTION_WITH_COVER_TYPE
                    VIDEO_SQUARE_CARD_COLLECTION -> return VIDEO_SQUARE_CARD_COLLECTION_TYPE
                    VIDEO_BANNER_THREE -> return VIDEO_BANNER_THREE_TYPE
                }
                return VIDEO_FOLLOW_CARD_TYPE
            }
        }
        with(multiTypeDelegate) {
            registerItemType(VIDEO_BANNER_TYPE, R.layout.layout_card_banner)
            registerItemType(VIDEO_FOLLOW_CARD_TYPE, R.layout.layout_follow_card)
            registerItemType(VIDEO_HORIZONTAL_SCROLL_CARD_TYPE, R.layout.layout_horizontal_scroll_card)
            registerItemType(VIDEO_COLLECTION_WITH_COVER_TYPE, R.layout.layout_collection_with_cover)
            registerItemType(VIDEO_SQUARE_CARD_COLLECTION_TYPE, R.layout.layout_square_collection)
            registerItemType(VIDEO_BANNER_THREE_TYPE, R.layout.layout_follow_card)
        }

    }

    override fun convert(helper: BaseViewHolder?, item: ItemList) {
        when (helper?.itemViewType) {
            VIDEO_BANNER_TYPE -> setBannerInfo(helper, item)
            VIDEO_FOLLOW_CARD_TYPE -> setFollowCardInfo(helper, item)
            VIDEO_HORIZONTAL_SCROLL_CARD_TYPE -> setHorizontalScrollCardInfo(helper, item.data.itemList)
            VIDEO_COLLECTION_WITH_COVER_TYPE -> setCollectionCardWithCoverInfo(helper, item.data)
            VIDEO_SQUARE_CARD_COLLECTION_TYPE -> setSquareCollectionInfo(helper, item.data.itemList)
            VIDEO_BANNER_THREE_TYPE -> setBanner3Info(helper, item.data)
        }
    }


    /**
     * 设置视频banner信息
     */
    private fun setBannerInfo(helper: BaseViewHolder, item: ItemList) {
        val imageView = helper.getView<SimpleDraweeView>(R.id.iv_image)
        imageView.setImageURI(item.data.image)
    }


    /**
     * 设置单视频卡片信息
     */
    private fun setFollowCardInfo(helper: BaseViewHolder, item: ItemList) {
        val info = item.data
        val cardNormalBottom = helper.getView<CardNormalBottom>(R.id.card_bottom)
        with(cardNormalBottom) {
            setTitle(info.header.title)
            setDescription(info.header.description)
            setIconUrl(info.header.icon)
            setIconType(info.header.iconType == "round")//设置图标形状
        }

        val eliteView = helper.getView<EliteImageView>(R.id.elite_view)
        with(eliteView) {
            setImageUrl(info.content.data.cover.feed)
            setDailyVisible(info.content.data.library == "DAILY")
        }

    }

    /**
     * 设置banner3（广告信息）信息
     */
    private fun setBanner3Info(helper: BaseViewHolder, item: ItemBean) {
        val cardNormalBottom = helper.getView<CardNormalBottom>(R.id.card_bottom)
        with(cardNormalBottom) {
            setTitle(item.title)
            setDescription(item.description)
            setIconUrl(item.header.icon)
            setIconType(item.header.iconType == "round")//设置图标形状
            setMoreOperateVisible(false)
        }

        val eliteView = helper.getView<EliteImageView>(R.id.elite_view)
        with(eliteView) {
            setImageUrl(item.image)
            setDailyVisible(false)
            setTranslateText(mContext.getString(R.string.advert))
        }
    }


    /**
     * 设置水平滚动卡片信息
     */
    private fun setHorizontalScrollCardInfo(helper: BaseViewHolder, itemList: MutableList<ItemList>) {
        val banner = helper.getView<Banner>(R.id.banner)
        with(banner) {
            setImageLoader(FrescoImageLoader())
            setImages(getHorizonTalCardUrl(itemList))
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            setIndicatorGravity(BannerConfig.CENTER)
            isAutoPlay(true)
            start()
            setDelayTime(5000)

        }
    }

    /**
     * 获取水平卡片图片地址
     */
    private fun getHorizonTalCardUrl(itemList: MutableList<ItemList>) = itemList.map { it.data.image }


    /**
     * 设置集合卡片信息
     */
    private fun setCollectionCardWithCoverInfo(helper: BaseViewHolder, item: ItemBean) {
        val eliteImageView = helper.getView<EliteImageView>(R.id.iv_image)
        val collectionRecycler = helper.getView<RecyclerView>(R.id.rv_collection_cover_recycler)
        val collectionCardCoverAdapter = CollectionCardCoverAdapter(item.itemList)
        collectionRecycler.isNestedScrollingEnabled = false
        collectionRecycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        collectionRecycler.adapter = collectionCardCoverAdapter
        eliteImageView.setImageUrl(item.header.cover)
        eliteImageView.setArrowVisible(true)

    }

    /**
     * 设置矩形卡片信息
     */
    private fun setSquareCollectionInfo(helper: BaseViewHolder, itemList: MutableList<ItemList>) {
        val squareRecycler = helper.getView<RecyclerView>(R.id.rv_square_recycler)
        val showAllContainer = helper.getView<RelativeLayout>(R.id.ll_more_container)
        squareRecycler.isNestedScrollingEnabled = false
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