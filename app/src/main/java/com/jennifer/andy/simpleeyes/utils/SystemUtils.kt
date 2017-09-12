package com.jennifer.andy.simplemusic.utils

import java.lang.reflect.ParameterizedType


/**
 * Author:  andy.xwt
 * Date:    2017/9/5 19:56
 * Description:
 */

class SystemUtils {


    companion object {
        /**
         * 获取当前类上的泛型参数，并实例化
         *
         * @param any 当前类
         * @param index  类泛型参数角标
         * @param <T>    泛型实例化对象
         * @return 泛型实例化对象
         */
        fun <T> getGenericInstance(any: Any, index: Int): T? {
            try {
                val type = any.javaClass.genericSuperclass as ParameterizedType//获取当前类的父类泛型参数
                val clazz = type.actualTypeArguments[index] as Class<*>//获取泛型class
                return clazz.newInstance() as T?
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: ClassCastException) {

            }

            return null
        }
    }


}