package com.jennifer.andy.simpleeyes.utils

import com.jennifer.andy.simpleeyes.base.model.BaseModel
import com.jennifer.andy.simpleeyes.base.presenter.BasePresenter
import java.lang.reflect.ParameterizedType


/**
 * 获取当前类上的泛型参数，并实例化
 *
 * @param any 当前类
 * @param index  类泛型参数角标
 * @param <T>    泛型实例化对象
 * @return 泛型实例化对象
 */
fun <T> getGenericInstance(any: Any, index: Int): T {
    try {
        val type = any.javaClass.genericSuperclass as ParameterizedType//获取当前类的父类泛型参数
        val clazz = type.actualTypeArguments[index] as Class<T>//获取泛型class
        val instance = clazz.newInstance()
        return if (instance is BasePresenter<*> || instance is BaseModel) {
            instance
        } else {
            throw IllegalStateException("if you use mvp user must support generic!!!")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        throw IllegalStateException("translate fail!!")
    }
}


