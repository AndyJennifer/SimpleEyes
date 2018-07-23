package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import android.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.ui.base.BaseAppCompatActivity
import com.jennifer.andy.simpleeyes.utils.kotlin.bindView


/**
 * Author:  andy.xwt
 * Date:    2018/7/20 16:24
 * Description:网页展示(设置了标题，设置了能否分享）
 * jvmField注解注释的不会生成get与set方法。且为public
 */
@Route(path = "/AndyJennifer/webview/")
class WebViewActivity : BaseAppCompatActivity() {

    private val mToolbar: Toolbar by bindView(R.id.tool_bar)
    private val mIvShare: ImageView by bindView(R.id.iv_share)
    private val mWebView: WebView by bindView(R.id.web_view)

    @Autowired
    @JvmField
    var title: String? = null

    @Autowired
    @JvmField
    var url: String? = null

    @Autowired
    @JvmField
    var nid: String? = null

    @Autowired
    @JvmField
    var tid: String? = null

    @Autowired
    @JvmField
    var cookie: String? = null

    @Autowired
    @JvmField
    var udid: String? = null

    @Autowired
    @JvmField
    var shareable: Boolean? = false


    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        initToolBar(mToolbar, title)
        initWebView()
    }

    private fun initWebView() {
        val webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.loadsImagesAutomatically = true
        webSettings.defaultTextEncodingName = "utf-8"
        mWebView.loadUrl(url)
    }

    override fun getContentViewLayoutId() = R.layout.activity_webview
}