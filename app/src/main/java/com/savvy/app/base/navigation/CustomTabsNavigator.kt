package com.savvy.app.base.navigation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.annotation.AnimRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.withStyledAttributes
import androidx.core.net.toUri
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.savvy.app.R

@Navigator.Name("customTabs")
class CustomTabsNavigator(private val context: Context) :
    Navigator<CustomTabsNavigator.Destination>() {
    companion object {
        private const val ARG_URL = "url"
    }

    override fun createDestination() = Destination(this)

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ): NavDestination? {
        val url = args?.getString(ARG_URL, "https://scghome.com/") ?: "https://scghome.com/"
        CustomTabsIntent.Builder().apply {
            setShowTitle(destination.showTitle)
            setStartAnimations(
                context,
                destination.browserStartAnimation,
                destination.activityExitAnimation
            )
            setExitAnimations(
                context,
                destination.browserExitAnimation,
                destination.activityEnterAnimation
            )
        }.build().launchUrl(
            context, url.toUri()
        )

        return null
    }

    override fun popBackStack() = true

    @NavDestination.ClassType(Activity::class)
    class Destination(navigator: Navigator<out NavDestination>) : NavDestination(navigator) {
        var showTitle: Boolean = true
        @AnimRes
        var browserStartAnimation: Int = R.anim.slide_in_right
        @AnimRes
        var activityExitAnimation: Int = R.anim.slide_out_left
        @AnimRes
        var browserExitAnimation: Int = R.anim.slide_in_left
        @AnimRes
        var activityEnterAnimation: Int = R.anim.slide_out_right

        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)

            context.withStyledAttributes(attrs, R.styleable.CustomTabsNavigator) {
                showTitle = getBoolean(R.styleable.CustomTabsNavigator_showTitle, true)
                browserStartAnimation = getResourceId(
                    R.styleable.CustomTabsNavigator_browserStartAnimation,
                    R.anim.slide_in_right
                )
                activityExitAnimation = getResourceId(
                    R.styleable.CustomTabsNavigator_activityExitAnimation,
                    R.anim.slide_out_left
                )
                browserExitAnimation = getResourceId(
                    R.styleable.CustomTabsNavigator_browserExitAnimation,
                    R.anim.slide_in_left
                )
                activityEnterAnimation = getResourceId(
                    R.styleable.CustomTabsNavigator_activityEnterAnimation,
                    R.anim.slide_out_right
                )
            }
        }
    }
}
