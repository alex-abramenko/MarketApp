package com.alxabr.market_product_viewer.view

internal sealed interface ProductViewerUiEvent {
    object OnFavoriteChanged : ProductViewerUiEvent
    object OnToBasketClick : ProductViewerUiEvent
}