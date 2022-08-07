package com.savvy.app.base

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.airbnb.epoxy.GlidePreloadRequestHolder
import com.airbnb.epoxy.preload.EpoxyModelPreloader
import com.airbnb.epoxy.preload.ViewMetadata
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.savvy.app.R
import com.savvy.core.base.BaseAndroidViewModel
import com.savvy.core.base.BaseViewModel
import com.savvy.core.base.epoxy.EpoxyViewBindingModelWithHolder
import com.savvy.core.base.interfaces.ConsumableEvent
import com.savvy.core.base.observe
import com.savvy.domain.SchedulersFacade
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment(@LayoutRes private val layoutRes: Int) :
    BottomSheetDialogFragment(),
    ConsumableEvent {
    companion object {
        @JvmStatic
        private val TAG = BaseBottomSheetDialogFragment::class.java.simpleName
    }

    val context: Context
        @JvmName("getNonNullContext")
        get() = requireContext()

    val activity: FragmentActivity
        @JvmName("getNonNullActivity")
        get() = requireActivity()

    val fragmentManager: FragmentManager
        @JvmName("getNonNullFragmentManager")
        get() = parentFragmentManager

    @Inject
    lateinit var schedulersFacade: SchedulersFacade

    @Inject
    lateinit var epoxyModelPreloader:
        EpoxyModelPreloader<EpoxyViewBindingModelWithHolder<*>, ViewMetadata?, GlidePreloadRequestHolder>

    // Data Members
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(context) }
    private var toolbar: Toolbar? = null
    protected var subscriptions = CompositeDisposable()
    protected var screenHeightPercent = 0f
        set(value) {
            field = if (value in 0f..1f) value else field
        }

    // Tracking
    protected abstract val screenName: String
    protected open var trackingMap = mapOf<String, Any>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        subscriptions = CompositeDisposable()
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view, savedInstanceState)
        viewModelList.forEach { viewModel ->
            when (viewModel) {
                is BaseViewModel -> {
                    viewModel.loadingLiveEvent
                        .observe(viewLifecycleOwner) { isLoading ->
                            if (isLoading) {
                                showProgressDialog()
                            } else {
                                hideProgressDialog()
                            }
                        }

                    viewModel.errorLiveEvent
                        .observe(viewLifecycleOwner) { messageRes ->
                            Timber.e(getString(messageRes))
                        }

                    viewModel.errorMessageLiveEvent
                        .observe(viewLifecycleOwner) { message ->
                            Timber.e(message)
                        }
                }
                is BaseAndroidViewModel -> {
                    viewModel.loadingLiveEvent
                        .observe(viewLifecycleOwner) { isLoading ->
                            if (isLoading) {
                                showProgressDialog()
                            } else {
                                hideProgressDialog()
                            }
                        }

                    viewModel.errorLiveEvent
                        .observe(viewLifecycleOwner) { messageRes ->
                            Timber.e(getString(messageRes))
                        }

                    viewModel.errorMessageLiveEvent
                        .observe(viewLifecycleOwner) { message ->
                            Timber.e(message)
                        }
                }
            }
        }
    }

    private fun initInstance(view: View, savedInstanceState: Bundle?) {
        toolbar = view.findViewById(R.id.toolbar)
        toolbar?.apply {
            try {
                val rootDestinations = AppBarConfiguration(
                    setOf(
                        R.id.homeFragment
                    )
                )

                setupWithNavController(findNavController(), rootDestinations)
                setOnMenuItemClickListener {
                    false
                }
            } catch (ignored: Exception) {
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomSheet =
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(R.color.transparent)
                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true

                if (screenHeightPercent != 0f) {
                    val dialogHeight = resources.displayMetrics.heightPixels * screenHeightPercent
                    bottomSheet.layoutParams.height = dialogHeight.toInt()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        subscriptions.clear()
        super.onDestroyView()
    }

    protected fun showProgressDialog() {
        try {
            progressDialog.show()
        } catch (e: WindowManager.BadTokenException) {
            Timber.e(e)
        }

        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.view_progress_dialog)
    }

    protected fun hideProgressDialog() {
        progressDialog.dismiss()
    }

    protected fun hideSoftKeyboard() {
        activity.currentFocus?.let {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    protected fun showSoftKeyboard() {
        activity.currentFocus?.let {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
