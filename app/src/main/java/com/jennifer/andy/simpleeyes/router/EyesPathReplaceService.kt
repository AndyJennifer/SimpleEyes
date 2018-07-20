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
 * 这里发现开眼的ActionUrl不是正常的scheme格式，所以这里要对其进行替换操作
 * 它的是：eyepetizer://pgc/detail/1065/?title=DELISH%20KITCHEN%20%E5%8F%AF%E5%8F%A3%E5%8E%A8%E6%88%BF&userType=PGC&tabIndex=1
 * 所以当我们通过Uri跳转的时候会出现问题。故写成eyepetizer://github.com/?title=DELISH%20KITCHEN%20%E5%8F%AF%E5%8F%A3%E5%8E%A8%E6%88%BF&userType=PGC&tabIndex=1?id=1065
 */
@Route(path = "/github/AndyJennifer")
class EyesPathReplaceService : PathReplaceService {

    override fun forString(path: String): String {
        return path
    }

    override fun forUri(uri: Uri): Uri {

        if (uri.scheme == "eyepetizer") {
            val uriStr = uri.toString()
            val split = uriStr.split("eyepetizer://")

            //获取id
            val pt = Pattern.compile("/\\d+/")
            val matcher = pt.matcher(split[1])
            var id = 0
            if (matcher.find()) {
                id = matcher.group().replace("/", "").toInt()
            }
            //替换/1065/为空字符串
            val uriWithoutNumber = split[1].replace(Regex("/\\d+/"), "")

            return Uri.parse("eyepetizer://github.com/$uriWithoutNumber&?id=$id")
        }
        return uri
    }

    override fun init(context: Context?) {

    }
}
