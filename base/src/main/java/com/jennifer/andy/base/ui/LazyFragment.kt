package com.jennifer.andy.base.ui

import androidx.fragment.app.Fragment


/**
 * Author:  andy.xwt
 * Date:    2020-01-07 22:11
 * Description:支持栏加载的fragment，需要配合 [com.jennifer.andy.base.adapter.FragmentLazyPagerAdapter] 使用
 */

open class LazyFragment : Fragment() {

    private var isLoaded = false

    override fun onResume() {
        super.onResume()
        if (!isLoaded) {
            lazyInit()
            isLoaded = true
        }
    }

    open fun lazyInit() {}

}