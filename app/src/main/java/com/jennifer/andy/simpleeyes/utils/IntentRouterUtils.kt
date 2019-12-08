package com.jennifer.andy.simpleeyes.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import com.jennifer.andy.simpleeyes.utils.ext.intentFor

/**
 * 跳转到相应的activity 并携带bundle数据
 */
inline fun <reified T : Any> Activity.readyGo(extras: Map<String, Any> = emptyMap()) {
    val intent = intentFor<T>(extras)
    startActivity(intent)
}

/**
 * 跳转到相应的activity,并携带bundle数据，接收返回码
 */
inline fun <reified T : Any> Activity.readyGoForResult(extras: Map<String, Any> = emptyMap(), requestCode: Int) {
    val intent = intentFor<T>(extras)
    startActivityForResult(intent, requestCode)
}

/**
 * 跳转到相应的activity并携带bundle数据，然后干掉当前Activity
 *
 */
inline fun <reified T : Any> Activity.readyGoThenKillSelf(extras: Map<String, Any> = emptyMap()) {
    val intent = intentFor<T>(extras)
    startActivity(intent)
    finish()
}


/**
 * 跳转到相应的activity 并携带bundle数据
 */
inline fun <reified T : Any> Fragment.readyGo(extras: Map<String, Any> = emptyMap()) {
    val intent = intentFor<T>(extras)
    startActivity(intent)
}

/**
 * 跳转到相应的activity,并携带bundle数据，接收返回码
 */
inline fun <reified T : Any> Fragment.readyGoForResult(extras: Map<String, Any> = emptyMap(), requestCode: Int) {
    val intent = intentFor<T>(extras)
    startActivityForResult(intent, requestCode)
}

/**
 * 跳转到相应的activity并携带bundle数据，然后干掉当前Fragment所属Activity
 *
 */
inline fun <reified T : Any> Fragment.readyGoThenKillSelf(extras: Map<String, Any> = emptyMap()) {
    val intent = intentFor<T>(extras)
    startActivity(intent)
    requireActivity().finish()
}




