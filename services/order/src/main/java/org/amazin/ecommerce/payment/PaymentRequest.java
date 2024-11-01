package org.amazin.ecommerce.payment;

import org.amazin.ecommerce.customer.CustomerResponse;
import org.amazin.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
