package com.itzel.fabulash.events

import android.view.View
import com.itzel.fabulash.models.DataMyAppointments

interface OnClickListenerMyAppointments {
    fun onClick(myAppointments: DataMyAppointments,view: View, position: Int)
}