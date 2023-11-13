package com.itzel.fabulash.events

import com.itzel.fabulash.models.Cards

interface OnClickListenerCards {
    fun onClick(card: Cards, position: Int)
}