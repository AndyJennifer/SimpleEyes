package com.jennifer.andy.simpleeyes.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * 跳转到相应的activity 并携带bundle数据
 */
fun Activity.readyGo(clazz: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, clazz)
    bundle?.let {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

/**
 * 跳转到相应的activity,并携带bundle数据，接收返回码
 */
fun Activity.readyGoForResult(clazz: Class<*>, bundle: Bundle? = null, requestCode: Int) {
    val intent = Intent(this, clazz)
    bundle?.let {
        intent.putExtras(bundle)
    }
    startActivityForResult(intent, requestCode)
}

/**
 * 跳转到相应的activity并携带bundle数据，然后干掉当前Activity
 *
 */
fun Activity.readyGoThenKillSelf(clazz: Class<out Any>, bundle: Bundle? = null) {
    val intent = Intent(this, clazz)
    bundle?.let {
        intent.putExtras(bundle)
    }
    startActivity(intent)
    finish()
}


/**
 * 跳转到相应的activity 并携带bundle数据
 */
fun Fragment.readyGo(clazz: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(requireActivity(), clazz)
    bundle?.let {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

/**
 * 跳转到相应的activity,并携带bundle数据，接收返回码
 */
fun Fragment.readyGoForResult(clazz: Class<*>, bundle: Bundle? = null, requestCode: Int) {
    val intent = Intent(requireActivity(), clazz)
    bundle?.let {
        intent.putExtras(bundle)
    }
    startActivityForResult(intent, requestCode)
}

/**
 * 跳转到相应的activity并携带bundle数据，然后干掉当前Fragment所属Activity
 *
 */
fun Fragment.readyGoThenKillSelf(clazz: Class<out Any>, bundle: Bundle? = null) {
    val intent = Intent(requireActivity(), clazz)
    bundle?.let {
        intent.putExtras(bundle)
    }
    startActivity(intent)
    requireActivity().finish()
}
