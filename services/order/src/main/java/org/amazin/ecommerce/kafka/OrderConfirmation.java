package org.amazin.ecommerce.kafka;

import org.amazin.ecommerce.customer.CustomerResponse;
import org.amazin.ecommerce.order.PaymentMethod;
import org.amazin.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
