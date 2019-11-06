package com.jennifer.andy.simpleeyes.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jennifer.andy.simpleeyes.R


/**
 * Author:  andy.xwt
 * Date:    2019-11-04 22:48
 * Description:作为导航页的空白fragment
 */

class NavigationEmptyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(context, R.layout.fragment_navigation_empty, null)
    }
}