package com.ecommerce.platform.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.platform.entity.NotificationDelivery;
import com.ecommerce.platform.entity.NotificationDeliveryStatus;
import com.ecommerce.platform.exception.NotificationDeliveryNotFoundException;
import com.ecommerce.platform.repository.NotificationDeliveryRepository;

@Service
@Transactional
public class NotificationDeliveryServiceImpl implements NotificationDeliveryService {

    private static final Logger logger =
            LoggerFactory.getLogger(NotificationDeliveryServiceImpl.class);

    @Value("${notification.retry.max-attempts}")
    private int maxRetryCount;

    private final NotificationDeliveryRepository notificationDeliveryRepository;

    public NotificationDeliveryServiceImpl(
            NotificationDeliveryRepository notificationDeliveryRepository) {

        this.notificationDeliveryRepository = notificationDeliveryRepository;
    }

    @Override
    public void processDelivery(Long deliveryId) {

        NotificationDelivery delivery = getDeliveryById(deliveryId);

        logger.info("Processing notification delivery with id: {}", deliveryId);

        try {

            markAsProcessing(delivery);

            deliverNotification(delivery);

            markAsSent(delivery);

            logger.info("Notification delivery {} completed successfully.", deliveryId);

        } catch (Exception ex) {

            logger.error("Notification delivery {} failed.", deliveryId, ex);

            String failureReason = ex.getMessage() != null
                    ? ex.getMessage()
                    : "Unknown error";

            markAsFailed(delivery, failureReason);
        }
    }

    @Override
    public void recoverStuckDelivery(Long deliveryId) {

        logger.warn("Recovering stuck notification delivery with id: {}", deliveryId);

        NotificationDelivery delivery = getDeliveryById(deliveryId);

        markAsFailed(delivery, "Notification processing timed out.");
    }

    // ==========================
    // Helper Methods
    // ==========================

    private void deliverNotification(NotificationDelivery delivery) {

        switch (delivery.getNotificationChannel()) {

            case EMAIL:
                logger.info("Sending EMAIL notification. Delivery Id: {}", delivery.getId());
                break;

            case PUSH:
                logger.info("Sending PUSH notification. Delivery Id: {}", delivery.getId());
                break;

            case SMS:
                logger.info("Sending SMS notification. Delivery Id: {}", delivery.getId());
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported notification channel: "
                                + delivery.getNotificationChannel());
        }
    }

    private void markAsProcessing(NotificationDelivery delivery) {

        logger.debug("Marking notification {} as PROCESSING.", delivery.getId());

        delivery.setNotificationDeliveryStatus(NotificationDeliveryStatus.PROCESSING);
        delivery.setFailureReason(null);
    }

    private void markAsSent(NotificationDelivery delivery) {

        logger.debug("Marking notification {} as SENT.", delivery.getId());

        delivery.setNotificationDeliveryStatus(NotificationDeliveryStatus.SENT);
        delivery.setSentAt(LocalDateTime.now());
        delivery.setFailureReason(null);
    }

    private void markAsFailed(NotificationDelivery delivery, String failureReason) {

        int retryCount = delivery.getRetryCount() + 1;

        delivery.setRetryCount(retryCount);

        NotificationDeliveryStatus status =
                retryCount >= maxRetryCount
                        ? NotificationDeliveryStatus.ABANDONED
                        : NotificationDeliveryStatus.FAILED;

        delivery.setNotificationDeliveryStatus(status);
        delivery.setFailureReason(failureReason);

        logger.warn(
                "Notification {} marked as {}. Retry Count: {}. Reason: {}",
                delivery.getId(),
                status,
                retryCount,
                failureReason);
    }

    private NotificationDelivery getDeliveryById(Long deliveryId) {

        return notificationDeliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new NotificationDeliveryNotFoundException(
                        "Notification delivery not found with id: " + deliveryId));
    }
}