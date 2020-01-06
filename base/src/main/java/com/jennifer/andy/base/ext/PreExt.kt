package com.jennifer.andy.base.ext

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * Author:  andy.xwt
 * Date:    2019-11-29 19:27
 * Description:高阶函数：https://www.kotlincn.net/docs/reference/lambdas.html
 */


inline fun SharedPreferences.boolean(key: String? = null, defaultValue: Boolean = false): ReadWriteProperty<Any, Boolean> {
    return delegate(key, defaultValue, SharedPreferences::getBoolean, SharedPreferences.Editor::putBoolean)
}

inline fun <T> SharedPreferences.delegate(
        key: String? = null,
        defaultValue: T,
        crossinline getter: SharedPreferences.(String, T) -> T,
        crossinline setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
): ReadWriteProperty<Any, T> = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T =
            getter(key ?: property.name, defaultValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        edit().setter(key ?: property.name, value).apply()
    }
}

