package com.jennifer.andy.simpleeyes.player.view

import android.content.Context


/**
 * Author:  andy.xwt
 * Date:    2020/3/9 23:26
 * Description:
 */

class ControllerViewFactory {

    companion object {
        const val TINY_MODE = 0
        const val FULL_SCREEN_MODE = 1
    }

    fun create(showMode: Int, context: Context): ControllerView {
        return when (showMode) {
            TINY_MODE -> TinyControllerView(context)
            FULL_SCREEN_MODE -> FullScreenControllerView(context)
            else -> throw IllegalArgumentException("not correct showMode")
        }
    }
}
