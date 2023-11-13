package com.itzel.fabulash.events

import com.itzel.fabulash.Cards

interface OnClickListenerDeleteCards {
    fun onClick(card: Cards, position: Int)

}