package com.mk.sepetandroid.presentation.order

sealed class OrderEvent {
    data object GetOrders : OrderEvent()
    data object CloseDialog : OrderEvent()
}