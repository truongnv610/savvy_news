package com.savvy.domain.base.usecase

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.location.Location
import android.os.Handler
import android.os.Looper
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import javax.inject.Inject


class FindCurrentLocationUseCase @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {

    companion object {
        private const val REQUEST_LOCATION_TIMEOUT = 15000L
    }

    fun execute(activity: Activity): Single<Location> {
        return Single.create { emitter ->
            val locationRequest = LocationRequest.create().apply {
                interval = 60000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            }
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build()).apply {
                addOnSuccessListener {
                    startLocationUpdates(emitter, locationRequest)
                }
                addOnFailureListener { exception ->
                    if (exception is ResolvableApiException) {
                        try {
                            exception.startResolutionForResult(activity, 0)
                            startLocationUpdates(emitter, locationRequest)
                        } catch (sendEx: IntentSender.SendIntentException) {
                            if (!emitter.isDisposed) {
                                emitter.onError(Throwable())
                            }
                        }
                    } else {
                        if (!emitter.isDisposed) {
                            emitter.onError(Throwable())
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(emitter: SingleEmitter<Location>, locationRequest: LocationRequest) {
        val locationTimeoutHandler = Handler(Looper.getMainLooper())
        val mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationTimeoutHandler.removeCallbacksAndMessages(null)
                fusedLocationProviderClient.removeLocationUpdates(this)
                val location = locationResult.locations.firstOrNull()
                if (location != null && !emitter.isDisposed) {
                    emitter.onSuccess(location)
                } else if (!emitter.isDisposed) {
                    emitter.onError(Throwable())
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            mLocationCallback,
            Looper.getMainLooper()
        )
        locationTimeoutHandler.postDelayed({
            mLocationCallback.onLocationResult(LocationResult.create(emptyList()))
        }, REQUEST_LOCATION_TIMEOUT)
    }
}