package com.itzel.fabulash.events

import com.itzel.fabulash.models.Cards

interface OnClickListenerDeleteCards {
    fun onClick(card: Cards, position: Int)

}