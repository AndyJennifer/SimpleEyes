package com.jennifer.andy.simpleeyes.router

import android.content.Context
import android.net.Uri
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.PathReplaceService
import java.util.regex.Pattern


/**
 * Author:  andy.xwt
 * Date:    2018/7/20 14:08
 * Description:
 * 这里发现开眼的ActionUrl不是正常的scheme格式，因为当我们通过Uri跳转的时候会出现问题。所以这里要对其进行替换操作
 */
@Route(path = "/github/AndyJennifer")
class EyesPathReplaceService : PathReplaceService {


    companion object {
        const val HOST = "eyepetizer://github.com/"
        const val SCHEME = "eyepetizer"
        const val ONE_LEVEL = "AndyJennifer/"
    }

    override fun forString(path: String): String {
        return path
    }

    override fun forUri(uri: Uri): Uri {

        if (uri.scheme == SCHEME) {
            val uriStr = uri.toString()
            val split = uriStr.split("eyepetizer://")
            when {
                uriStr.contains("pgc/detail") -> //处理详情
                    return setPageDetailUrl(split)
                uriStr.contains("webview") -> //处理webview
                    return setWebViewUrl(split)
                uriStr.contains("detail") -> //处理webview中点击跳转
                    return setWebVideoUrl(split)
                uriStr.contains("ranklist") ->//处理排行榜
                    return setRankListUrl(split)
                uriStr.contains("campaign") ->//处理专题
                    return setTopicUrl(split)
                uriStr.contains("tag") ->//处理360全景
                    return setTagUrl(split)
                else -> {
                }
            }


        }
        return uri
    }


    /**
     * 处理视频详情url
     * eyepetizer://pgc/detail/1065/?title=DELISH%20KITCHEN%20%E5%8F%AF%E5%8F%A3%E5%8E%A8%E6%88%BF&userType=PGC&tabIndex=1
     * 替换为：
     * eyepetizer://github.com/pag/detail/?title=DELISH%20KITCHEN%20%E5%8F%AF%E5%8F%A3%E5%8E%A8%E6%88%BF&userType=PGC&tabIndex=1?id=1065
     */
    private fun setPageDetailUrl(split: List<String>): Uri {
        //获取id
        val pt = Pattern.compile("/\\d+/")
        val matcher = pt.matcher(split[1])
        var id = 0
        if (matcher.find()) {
            id = matcher.group().replace("/", "").toInt()
        }
        //替换/1065/为空字符串
        val uriWithoutNumber = split[1].replace(Regex("/\\d+/"), "")
        return Uri.parse("$HOST$uriWithoutNumber&id=$id")
    }

    /**
     * 处理webViewUrl
     * eyepetizer://webview/?title=%E5%B9%BF%E5%9C%BA&url=http%3A%2F%2Fwww.kaiyanapp.com%2Fcampaign%2Ftag_square%2Ftag_square.html%3Fnid%3D1207%26tid%3D845%26cookie%3D%26udid%3Dd0f6190461864a3a978bdbcb3fe9b48709f1f390%26shareable%3Dtrue
     * 替换为：eyepetizer://github.com/AndyJennifer/webview/?title=%E5%B9%BF%E5%9C%BA&url=http%3A%2F%2Fwww.kaiyanapp.com%2Fcampaign%2Ftag_square%2Ftag_square.html%3Fnid%3D1207%26tid%3D845%26cookie%3D%26udid%3Dd0f6190461864a3a978bdbcb3fe9b48709f1f390%26shareable%3Dtrue
     */
    private fun setWebViewUrl(split: List<String>): Uri {
        return Uri.parse("$HOST$ONE_LEVEL${split[1]}")
    }

    /**
     * 处理webView点击跳转视频信息
     * eyepetizer://detail/114760
     * 替换为：eyepetizer://github.com/AndyJennifer/detail?id =114760
     */
    private fun setWebVideoUrl(split: List<String>): Uri {
        //获取id
        val pt = Pattern.compile("/\\d+")
        val matcher = pt.matcher(split[1])
        var id = 0
        if (matcher.find()) {
            id = matcher.group().replace("/", "").toInt()
        }
        //替换/1065/为空字符串
        val uriWithoutNumber = split[1].replace(Regex("/\\d+"), "")
        return Uri.parse("$HOST$ONE_LEVEL$uriWithoutNumber?id=$id")
    }

    /**
     * 处理排行榜
     * eyepetizer://ranklist/
     * 替换为：eyepetizer://github.com/AndyJennifer/ranklist
     */
    private fun setRankListUrl(split: List<String>): Uri {
        val str = split[1].replace("/", "")
        return Uri.parse("$HOST$ONE_LEVEL$str")
    }

    /**
     * 处理热门专题，
     * eyepetizer://campaign/list/?title=%E4%B8%93%E9%A2%98
     * 替换为：eyepetizer://github.com/campaign/list?title=%E4%B8%93%E9%A2%98
     */
    private fun setTopicUrl(split: List<String>): Uri {
        val str = split[1].replace("/?", "?")
        return Uri.parse("$HOST$str")
    }

    /**
     * 处理360全景
     * eyepetizer://tag/658/?title=360%C2%B0%E5%85%A8%E6%99%AF
     * eyepetizer://github.com/AndyJennifer/tag?id=658&title=360%C2%B0%E5%85%A8%E6%99%AF
     */
    private fun setTagUrl(split: List<String>): Uri {
        //获取id
        val pt = Pattern.compile("/\\d+/")
        val matcher = pt.matcher(split[1])
        var id = 0
        if (matcher.find()) {
            id = matcher.group().replace("/", "").toInt()
        }
        //替换/658/为空字符串
        val uriWithoutNumber = split[1].replace(Regex("/\\d+/"), "")
        return Uri.parse("$HOST$ONE_LEVEL$uriWithoutNumber&id=$id")
    }

    override fun init(context: Context?) {

    }
}
