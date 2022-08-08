package com.savvy.app.network_exception

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jakewharton.rxbinding4.view.clicks
import com.savvy.app.R
import com.savvy.app.base.BaseBottomSheetDialogFragment
import com.savvy.app.base.model.NetworkException
import com.savvy.app.databinding.BottomSheetNetworkExceptionBinding
import com.savvy.core.base.delegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import timber.log.Timber

@AndroidEntryPoint
class NetworkExceptionBottomSheetDialogFragment :
    BaseBottomSheetDialogFragment(R.layout.bottom_sheet_network_exception) {

    companion object {
        @JvmStatic
        private val TAG = NetworkExceptionBottomSheetDialogFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): NetworkExceptionBottomSheetDialogFragment {
            return NetworkExceptionBottomSheetDialogFragment().apply {
                arguments = Bundle()
            }
        }
    }

    private val mNetworkFragmentArgs by navArgs<NetworkExceptionBottomSheetDialogFragmentArgs>()
    private val mNetworkException by lazy { mNetworkFragmentArgs.networkException }

    private val mBinding by viewBinding(BottomSheetNetworkExceptionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance()
    }

    private fun initInstance() {
        with(mBinding) {
            if (mNetworkException is NetworkException.ConnectionError) {
                imageViewCheck.setImageResource(R.drawable.ic_wifi_off)
                textViewHeader.text = getString(R.string.network_no_connection_header)
                textViewTitle.text = getString(R.string.network_no_connection_title)
            } else if (mNetworkException is NetworkException.ResponseError) {
                imageViewCheck.setImageResource(R.drawable.ic_error_outline)
                textViewHeader.text = getString(R.string.common_error)
                textViewTitle.text = (mNetworkException as NetworkException.ResponseError).message
                    ?.takeIf { it.isNotEmpty() }
                    ?: getString(R.string.network_response_error_title)
            }
            subscriptions += buttonGoBack.clicks()
                .observeOn(schedulersFacade.ui)
                .subscribeBy(
                    onError = Timber::e,
                    onNext = {
                        findNavController().popBackStack()
                    }
                )
        }
    }

    override val screenName: String
        get() = TAG
}
