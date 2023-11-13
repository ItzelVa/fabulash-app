package com.itzel.fabulash.events

import com.itzel.fabulash.models.Employee

interface OnClickListenerEmployee {
    fun onClick(employee: Employee)
}