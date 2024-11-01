package org.amazin.ecommerce.notification.notification;

import lombok.*;
import org.amazin.ecommerce.notification.kafka.order.OrderConfirmation;
import org.amazin.ecommerce.notification.kafka.payment.PaymentConfirmation;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Notification {
    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime notificationTime;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;
}
