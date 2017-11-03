package com.jennifer.andy.simpleeyes.widget.font


/**
 * Author:  andy.xwt
 * Date:    2017/11/2 14:41
 * Description:字体常量 包括 方正兰亭细黑简体,方正兰亭中粗黑简体了，拉丁，，龙虾字体
 */
enum class FontType(var index: Int, var fontName: String, val path: String) {

    NORMAL(1, "Normal", "fonts/FZLanTingHeiS-L-GB-Regular.TTF"),
    BOLD(2, "Bold", "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"),
    FUTURE(3, "Future", "fonts/Futura-CondensedMedium.ttf"),
    LOBSTER(4, "Lobster", "fonts/Lobster-1.4.otf");

}
