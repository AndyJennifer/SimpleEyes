package com.jennifer.andy.base.ext.reactivex

import io.reactivex.subjects.BehaviorSubject


/**
 * Author:  andy.xwt
 * Date:    2019-12-24 00:59
 * Description:
 */

inline fun <reified T> BehaviorSubject<T>.copyMap(map: (T) -> T) {
    val oldValue: T? = value
    if (oldValue != null) {
        onNext(map(oldValue))
    } else {
        throw NullPointerException("BehaviorSubject<${T::class.java}> not contain value.")
    }
}