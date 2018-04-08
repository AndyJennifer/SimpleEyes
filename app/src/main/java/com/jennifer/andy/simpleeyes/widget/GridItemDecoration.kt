package com.jennifer.andy.simpleeyes.widget

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * Author:  andy.xwt
 * Date:    2018/4/8 11:15
 * Description:
 */

class GridItemDecoration constructor( val spanCount: Int, val spacing: Int, val isIncludeEdge: Boolean) : RecyclerView.ItemDecoration() {

    

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (isIncludeEdge) {
            // spacing - column * ((1f / spanCount) * spacing)
            outRect.left = spacing - column * spacing / spanCount
            // (column + 1) * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom
        } else {
            // column * ((1f / spanCount) * spacing)
            outRect.left = column * spacing / spanCount
            // spacing - (column + 1) * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }
}