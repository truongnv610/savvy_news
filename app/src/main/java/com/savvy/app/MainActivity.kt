package com.savvy.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.*
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.savvy.app.base.BaseActivity
import com.savvy.app.base.behavior.ScrollingViewWithBottomNavigationBehavior
import com.savvy.app.base.model.NetworkException
import com.savvy.app.databinding.ActivityMainBinding
import com.savvy.app.news.FragmentNewsListDirections
import com.savvy.core.base.observe
import com.savvy.core.base.provider.SystemUiVisibilityProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    companion object {
        @JvmStatic
        private val TAG = MainActivity::class.java.simpleName

        @JvmStatic
        val BROADCAST_NETWORK_EXCEPTION_ACTION = "ACTION_NETWORK_EXCEPTION"

        @JvmStatic
        val BROADCAST_NETWORK_EXCEPTION_EXTRA = "EXTRA_NETWORK_EXCEPTION"
    }

    // Injection
    @Inject
    lateinit var mSystemUiVisibilityProvider: SystemUiVisibilityProvider

    // Data Members
    private lateinit var mBinding: ActivityMainBinding
    private val mMainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            mBinding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(mBinding.root)
            showUIBar()
            initView()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        mMainViewModel.showSnackbarLiveEvent
            .observe(this) {
                showSnackBar(it.message, it.length)
            }
    }


    private fun initView() {
        with(mBinding) {
            ViewCompat.setOnApplyWindowInsetsListener(coordinatorLayout) { _, windowInsets ->
                val bottomInset =
                    windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                (((supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment)?.view?.layoutParams as? CoordinatorLayout.LayoutParams)?.behavior as? ScrollingViewWithBottomNavigationBehavior)?.navigationBarHeight =
                    bottomInset
                bottomNavigation.updatePadding(bottom = bottomInset)
                snackBarPosition.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = bottomInset
                }

                windowInsets
            }

            (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).navController.let {
                bottomNavigation.apply {
                    setupWithNavController(it)
                }
                it.addOnDestinationChangedListener { _, destination, _ ->
                    bottomNavigation.visibility = when (destination.id) {
                        R.id.homeFragment,
                        R.id.serviceFragment,
                        R.id.megaMenuFragment,
                        R.id.storeFragment,
                        R.id.promotionFragment,
                        R.id.fragmentTemplate -> View.VISIBLE
                        else -> {
                            View.GONE
                        }
                    }
                    hideSoftKeyboard()
                }
            }
            bottomNavigation.menu.forEachIndexed { _, item ->
            }
        }
    }

    private fun hideUIBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun showUIBar() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).show(WindowInsetsCompat.Type.systemBars())
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    private fun showSnackBar(message: String, length: Int) {
        with(mBinding) {
            Snackbar.make(coordinatorLayout, message, length).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navHostFragment).navigateUp() || super.onSupportNavigateUp()
    }

    override fun onResume() {
        registerReceiver(mOnNetworkExceptionReceiver, IntentFilter(BROADCAST_NETWORK_EXCEPTION_ACTION))
        super.onResume()
    }

    override fun onPause() {
        unregisterReceiver(mOnNetworkExceptionReceiver)
        super.onPause()
    }

    private val mOnNetworkExceptionReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.getParcelableExtra<NetworkException>(BROADCAST_NETWORK_EXCEPTION_EXTRA)
                    ?.let { networkException ->
                        val backStackEntry = try {
                            findNavController(R.id.navHostFragment).getBackStackEntry(R.id.dialogNetworkExceptionBottomSheetFragment)
                        } catch (t: Throwable) {
                            null
                        }
                        // Prevent duplicate Fragments in Back Stack
                        if (backStackEntry == null) {
                            findNavController(R.id.navHostFragment).navigate(
                                FragmentNewsListDirections.actionGlobalNetworkExceptionBottomSheetFragment(
                                    networkException = networkException
                                )
                            )
                        }
                    }
            }
        }
    }

    override val viewModelList: List<ViewModel>
        get() = super.viewModelList.toMutableList().apply {
          //  add(mLauncherViewModel)
            add(mMainViewModel)
        }
}
