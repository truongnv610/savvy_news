package com.savvy.core.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.savvy.core.R
import com.savvy.core.base.interfaces.ConsumableEvent
import com.savvy.domain.SchedulersFacade
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment(), ConsumableEvent {
    companion object {
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

    // Injection

    @Inject
    lateinit var schedulersFacade: SchedulersFacade

    private val progressDialog: ProgressDialog by lazy { ProgressDialog(context) }
    protected var subscriptions = CompositeDisposable()
    protected abstract val screenName: String

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val screenName = screenName
            .replace("Activity", "")
            .replace("DialogFragment", "")
            .replace("Fragment", "")
    }

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeDisposable()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
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

    protected fun showAlertDialog(
        @StringRes titleRes: Int,
        message: String,
        shouldFinish: Boolean
    ) {
        AlertDialog.Builder(context)
            .setTitle(getString(titleRes))
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialogInterface, _ ->
                dialogInterface.dismiss()
                if (shouldFinish) activity.finish()
            }
            .show()
    }
}