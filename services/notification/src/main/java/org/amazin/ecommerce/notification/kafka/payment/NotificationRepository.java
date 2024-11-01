package org.amazin.ecommerce.notification.kafka.payment;

import org.amazin.ecommerce.notification.notification.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {

}
