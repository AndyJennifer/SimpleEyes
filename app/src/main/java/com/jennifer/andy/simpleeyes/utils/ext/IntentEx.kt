package com.jennifer.andy.simpleeyes.utils.ext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable


/**
 * Author:  andy.xwt
 * Date:    2019-12-08 16:52
 * Description:根据传入的泛型创建Intent比填充相应的参数
 */


inline fun <reified T : Any> Context.intentFor(params: Map<String, Any>): Intent {
    return createIntent(this, T::class.java, params)
}

inline fun <reified T : Any> Fragment.intentFor(params: Map<String, Any>): Intent {
    return createIntent(requireContext(), T::class.java, params)
}

fun <T> createIntent(ctx: Context, clazz: Class<out T>, params: Map<String, Any>): Intent {
    val intent = Intent(ctx, clazz)
    if (params.isNotEmpty()) fillIntentArguments(intent, params)
    return intent
}


private fun fillIntentArguments(intent: Intent, params: Map<String, Any>) {
    params.keys.forEach { key ->
        when (val value = params[key]) {
            null -> intent.putExtra(key, null as Serializable?)
            is Int -> intent.putExtra(key, value)
            is Long -> intent.putExtra(key, value)
            is CharSequence -> intent.putExtra(key, value)
            is String -> intent.putExtra(key, value)
            is Float -> intent.putExtra(key, value)
            is Double -> intent.putExtra(key, value)
            is Char -> intent.putExtra(key, value)
            is Short -> intent.putExtra(key, value)
            is Boolean -> intent.putExtra(key, value)
            is Serializable -> intent.putExtra(key, value)
            is Bundle -> intent.putExtra(key, value)
            is Parcelable -> intent.putExtra(key, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> intent.putExtra(key, value)
                value.isArrayOf<String>() -> intent.putExtra(key, value)
                value.isArrayOf<Parcelable>() -> intent.putExtra(key, value)
                else -> error("Intent extra $key has wrong type ${value.javaClass.name}")
            }
            is IntArray -> intent.putExtra(key, value)
            is LongArray -> intent.putExtra(key, value)
            is FloatArray -> intent.putExtra(key, value)
            is DoubleArray -> intent.putExtra(key, value)
            is CharArray -> intent.putExtra(key, value)
            is ShortArray -> intent.putExtra(key, value)
            is BooleanArray -> intent.putExtra(key, value)
            else -> throw error("Intent extra $key has wrong type ${value.javaClass.name}")
        }
        return@forEach
    }
}