package com.alxabr.market_product_viewer

internal sealed interface ProductViewerUiEvent {
    object OnFavoriteChanged : ProductViewerUiEvent
    object OnToBasketClick : ProductViewerUiEvent
    object OnDescHiderClick : ProductViewerUiEvent
}