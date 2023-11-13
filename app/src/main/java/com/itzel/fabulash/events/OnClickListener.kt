package com.itzel.fabulash.events

import com.itzel.fabulash.models.Lashes

interface OnClickListener {
    fun onClick(lash: Lashes)
}