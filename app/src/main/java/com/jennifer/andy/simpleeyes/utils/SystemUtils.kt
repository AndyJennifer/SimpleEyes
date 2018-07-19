package com.jennifer.andy.simpleeyes.utils

import java.lang.reflect.ParameterizedType


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:56
 * Description:
 */

object SystemUtils {


    /**
     * 获取当前类上的泛型参数，并实例化
     *
     * @param any 当前类
     * @param index  类泛型参数角标
     * @param <T>    泛型实例化对象
     * @return 泛型实例化对象
     */
    @JvmStatic
    fun <T> getGenericInstance(any: Any, index: Int): T {
        try {
            val type = any.javaClass.genericSuperclass as ParameterizedType//获取当前类的父类泛型参数
            val clazz = type.actualTypeArguments[index] as Class<T>//获取泛型class
            return clazz.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
            throw IllegalStateException("translate fail!!")
        }
    }


}