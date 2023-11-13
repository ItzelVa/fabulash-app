package com.itzel.fabulash.events

import com.itzel.fabulash.Cards

interface OnClickListenerCards {
    fun onClick(card: Cards, position: Int)
}