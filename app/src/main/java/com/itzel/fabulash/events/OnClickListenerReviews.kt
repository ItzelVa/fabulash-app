package com.itzel.fabulash.events

import com.itzel.fabulash.models.Reviews

interface OnClickListenerReviews {
    fun onClick(review: Reviews, position: Int)
}