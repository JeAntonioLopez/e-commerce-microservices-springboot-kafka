package org.amazin.ecommerce.product;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "Product id requested")
        Integer productId,
        @NotNull(message = "Product quantity requested")
        double quantity
) {
}
