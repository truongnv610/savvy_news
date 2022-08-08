package com.savvy.app.base.behavior

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

open class HideBottomViewOnScrollBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    private var height = 0
    private var currentState: Int = STATE_SCROLLED_DOWN
    private var currentAnimator: ViewPropertyAnimator? = null

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        val paramsCompat = child.layoutParams as ViewGroup.MarginLayoutParams
        height = child.measuredHeight + paramsCompat.bottomMargin
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if (dyConsumed > 0) {
            slideUp(child)
        } else if (dyConsumed < 0) {
            slideDown(child)
        }
    }

    private fun slideUp(child: View) {
        if (currentState == STATE_SCROLLED_UP) {
            return
        }
        currentAnimator?.cancel()
        child.clearAnimation()
        currentState = STATE_SCROLLED_UP
        animateChildTo(
            child,
            0,
            250,
            LINEAR_OUT_SLOW_IN_INTERPOLATOR
        )
    }

    private fun slideDown(child: View) {
        if (currentState == STATE_SCROLLED_DOWN) {
            return
        }
        currentAnimator?.cancel()
        child.clearAnimation()
        currentState = STATE_SCROLLED_DOWN
        animateChildTo(
            child,
            height,
            200,
            FAST_OUT_LINEAR_IN_INTERPOLATOR
        )
    }

    private fun animateChildTo(
        child: View,
        targetY: Int,
        duration: Long,
        interpolator: TimeInterpolator
    ) {
        currentAnimator = child
            .animate()
            .translationY(targetY.toFloat())
            .setInterpolator(interpolator)
            .setDuration(duration)
            .setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        currentAnimator = null
                    }
                })
    }

    companion object {
        private const val STATE_SCROLLED_DOWN = 1
        private const val STATE_SCROLLED_UP = 2

        private val FAST_OUT_LINEAR_IN_INTERPOLATOR: TimeInterpolator = FastOutLinearInInterpolator()
        private val LINEAR_OUT_SLOW_IN_INTERPOLATOR: TimeInterpolator = LinearOutSlowInInterpolator()
    }
}
