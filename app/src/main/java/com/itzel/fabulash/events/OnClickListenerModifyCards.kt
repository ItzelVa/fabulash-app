package com.itzel.fabulash.events

import com.itzel.fabulash.Cards

interface OnClickListenerModifyCards {
    fun onClick(card: Cards, position: Int)
}