package com.itzel.fabulash.events

import com.itzel.fabulash.models.Cards

interface OnClickListenerModifyCards {
    fun onClick(card: Cards, position: Int)
}