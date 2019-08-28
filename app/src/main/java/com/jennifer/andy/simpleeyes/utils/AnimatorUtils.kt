package com.jennifer.andy.simpleeyes.utils


/**
 * Author:  andy.xwt
 * Date:    2019-08-28 20:09
 * Description:使用androidx.core:core-ktx中的工具类
 */

import android.animation.Animator
import android.support.annotation.RequiresApi

/**
 * Add an action which will be invoked when the animation has ended.
 *
 * @return the [Animator.AnimatorListener] added to the Animator
 * @see Animator.end
 */
fun Animator.doOnEnd(action: (animator: Animator) -> Unit) = addListener(onEnd = action)

/**
 * Add an action which will be invoked when the animation has started.
 *
 * @return the [Animator.AnimatorListener] added to the Animator
 * @see Animator.start
 */
fun Animator.doOnStart(action: (animator: Animator) -> Unit) = addListener(onStart = action)

/**
 * Add an action which will be invoked when the animation has been cancelled.
 *
 * @return the [Animator.AnimatorListener] added to the Animator
 * @see Animator.cancel
 */
fun Animator.doOnCancel(action: (animator: Animator) -> Unit) = addListener(onCancel = action)

/**
 * Add an action which will be invoked when the animation has repeated.
 * @return the [Animator.AnimatorListener] added to the Animator
 */
fun Animator.doOnRepeat(action: (animator: Animator) -> Unit) = addListener(onRepeat = action)

/**
 * Add an action which will be invoked when the animation has resumed after a pause.
 *
 * @return the [Animator.AnimatorPauseListener] added to the Animator
 * @see Animator.resume
 */
@RequiresApi(19)
fun Animator.doOnResume(action: (animator: Animator) -> Unit) = addPauseListener(onResume = action)

/**
 * Add an action which will be invoked when the animation has been paused.
 *
 * @return the [Animator.AnimatorPauseListener] added to the Animator
 * @see Animator.pause
 */
@RequiresApi(19)
fun Animator.doOnPause(action: (animator: Animator) -> Unit) = addPauseListener(onPause = action)

/**
 * Add a listener to this Animator using the provided actions.
 */
fun Animator.addListener(
        onEnd: ((animator: Animator) -> Unit)? = null,
        onStart: ((animator: Animator) -> Unit)? = null,
        onCancel: ((animator: Animator) -> Unit)? = null,
        onRepeat: ((animator: Animator) -> Unit)? = null
): Animator.AnimatorListener {
    val listener = object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animator: Animator) {
            onRepeat?.invoke(animator)
        }

        override fun onAnimationEnd(animator: Animator) {
            onEnd?.invoke(animator)
        }

        override fun onAnimationCancel(animator: Animator) {
            onCancel?.invoke(animator)
        }

        override fun onAnimationStart(animator: Animator) {
            onStart?.invoke(animator)
        }
    }
    addListener(listener)
    return listener
}

/**
 * Add a pause and resume listener to this Animator using the provided actions.
 */
@RequiresApi(19)
fun Animator.addPauseListener(
        onResume: ((animator: Animator) -> Unit)? = null,
        onPause: ((animator: Animator) -> Unit)? = null
): Animator.AnimatorPauseListener {
    val listener = object : Animator.AnimatorPauseListener {
        override fun onAnimationPause(animator: Animator) {
            onPause?.invoke(animator)
        }

        override fun onAnimationResume(animator: Animator) {
            onResume?.invoke(animator)
        }
    }
    addPauseListener(listener)
    return listener
}
