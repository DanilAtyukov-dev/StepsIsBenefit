package com.danilatyukov.linkedmoney.ui

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.R


class InfoDialogFragment(
    val title: String,
    val text: String,
    val ic: Int,
    val geo: Boolean = false,
    val policy: Boolean = false
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
                .setMessage(text)
                .setIcon(ic)

            if (geo) {
                builder.setPositiveButton(
                    App.it().getText(R.string.geolocationNotificationPositive)
                ) { dialog, id ->
                    dialog.cancel()
                    geolocationRequest()
                }
                    .setNegativeButton(
                        App.it().getText(R.string.geolocationNotificationNegative)
                    ) { dialog, id ->
                        dialog.cancel()
                        App.it().appComponent.appPreferences.setRequestGeolocation(false)
                    }

            } else if(policy){

                builder.setPositiveButton("ОК") { dialog, id ->
                    dialog.cancel()
                }
                builder.setNegativeButton("ЧИТАТЬ") { dialog, id ->
                    dialog.cancel()
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/@-216264240-privacy-policy-and-terms-of-service"))
                    startActivity(browserIntent)
                }



            }
            else builder.setPositiveButton("ОК") { dialog, id ->
                dialog.cancel()
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    fun geolocationRequest() { //запрос геолокации

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this.requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
        } else {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }
}