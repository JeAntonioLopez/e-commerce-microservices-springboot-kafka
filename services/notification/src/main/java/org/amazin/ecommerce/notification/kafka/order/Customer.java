package org.amazin.ecommerce.notification.kafka.order;

import org.springframework.data.annotation.Id;

public record Customer(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
