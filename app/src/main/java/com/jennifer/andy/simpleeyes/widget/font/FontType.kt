package com.jennifer.andy.simpleeyes.widget.font


/**
 * Author:  andy.xwt
 * Date:    2017/11/2 14:41
 * Description:字体常量 包括 方正兰亭细黑简体,方正兰亭中粗黑简体，拉丁，龙虾字体
 */
enum class FontType(var index: Int, var fontName: String, val path: String) {

    NORMAL(0, "Normal", "fonts/FZLanTingHeiS-L-GB-Regular.TTF"),//方正兰亭细黑简体
    BOLD(1, "Bold", "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"),//方正兰亭中粗黑简体
    FUTURE(2, "Future", "fonts/Futura-CondensedMedium.ttf"),//拉丁
    LOBSTER(3, "Lobster", "fonts/Lobster-1.4.otf");//龙虾字体

}
