package com.crazylegend.kotlinextensions.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresPermission
import java.util.concurrent.Executors


/**
 * Created by hristijan on 3/5/19 to long live and prosper !
 */

/**
 * Gets a single location update, returning the current location when found through a callback.
 *
 * This function does assume we have permission already.
 */
@Suppress("DEPRECATION")
@RequiresPermission(allOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION])
inline fun LocationManager.requestSingleUpdate(
    criteria: Criteria = Criteria(),
    crossinline onLocationHad: (location: Location) -> Unit = { _ -> }
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        getCurrentLocation("gps", null, Executors.newSingleThreadExecutor(), { location -> onLocationHad(location) })
    } else {
        requestSingleUpdate(criteria, { location -> onLocationHad(location) }, Looper.getMainLooper())
    }
}


inline fun Context.getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double, onNotAvailable: () -> Unit = {}): ObtainedLocationModel {

    val geocoder = Geocoder(this)

    val obtainedLocationModel = ObtainedLocationModel()

    try {

        val addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1) ?: emptyList()

        if (addresses.isNotEmpty()) {

            val returnedAddress = addresses[0]
            val city = addresses[0].locality
            val state = addresses[0].adminArea
            val country = addresses[0].countryName
            val postalCode = addresses[0].postalCode
            val knownName = addresses[0].featureName // Only if available else return NULL


            city?.let {
                obtainedLocationModel.city = it
            }

            state?.let {
                obtainedLocationModel.state = it
            }

            country?.let {
                obtainedLocationModel.country = it
            }

            postalCode?.let {
                obtainedLocationModel.postalCode = it
            }

            knownName?.let {
                obtainedLocationModel.knownName = it
            }


            for (i in 0..returnedAddress.maxAddressLineIndex) {
                obtainedLocationModel.address = returnedAddress.getAddressLine(i)
            }

        } else {
            onNotAvailable()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        onNotAvailable()
    }

    return obtainedLocationModel
}

