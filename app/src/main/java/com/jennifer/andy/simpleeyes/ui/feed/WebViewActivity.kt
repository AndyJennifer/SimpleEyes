package com.jennifer.andy.simpleeyes.ui.feed

import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jennifer.andy.simpleeyes.R
import com.jennifer.andy.simpleeyes.databinding.ActivityWebviewBinding
import com.jennifer.andy.simpleeyes.ui.base.BaseDataBindActivity


/**
 * Author:  andy.xwt
 * Date:    2018/7/20 16:24
 * Description:网页展示(设置了标题，设置了能否分享）
 * jvmField注解注释的不会生成get与set方法。且为public
 */
@Route(path = "/AndyJennifer/webview/")
class WebViewActivity : BaseDataBindActivity<ActivityWebviewBinding>() {

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
        initToolBar(mDataBinding.toolBar, title)
        initWebView()
    }

    private fun initWebView() {

        mDataBinding.webView.apply {

            settings.apply {
                javaScriptEnabled = true
                loadsImagesAutomatically = true
                defaultTextEncodingName = "utf-8"
                //5.0以上需要支持混合模式
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) = false
            }

        }.loadUrl(url)
    }

    override fun getContentViewLayoutId() = R.layout.activity_webview
}