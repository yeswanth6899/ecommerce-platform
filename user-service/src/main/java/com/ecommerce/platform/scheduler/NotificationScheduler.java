package com.ecommerce.platform.scheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ecommerce.platform.entity.NotificationDelivery;
import com.ecommerce.platform.entity.NotificationDeliveryStatus;
import com.ecommerce.platform.repository.NotificationDeliveryRepository;
import com.ecommerce.platform.service.NotificationDeliveryService;

@Component
public class NotificationScheduler {

    private static final Logger logger =
            LoggerFactory.getLogger(NotificationScheduler.class);

    private final NotificationDeliveryService notificationDeliveryService;
    private final NotificationDeliveryRepository notificationDeliveryRepository;

    @Value("${notification.processing.timeout}")
    private Duration processingTimeout;

    public NotificationScheduler(
            NotificationDeliveryService notificationDeliveryService,
            NotificationDeliveryRepository notificationDeliveryRepository) {

        this.notificationDeliveryService = notificationDeliveryService;
        this.notificationDeliveryRepository = notificationDeliveryRepository;
    }

    @Scheduled(fixedDelayString = "${notification.processing.delay}")
    public void processPendingDeliveries() {

        logger.info("Processing pending notifications...");

        List<NotificationDelivery> deliveries =
                notificationDeliveryRepository.findByNotificationDeliveryStatus(
                        NotificationDeliveryStatus.PENDING);

        processNotificationDeliveries(deliveries);
    }

    @Scheduled(fixedDelayString = "${notification.retry.delay}")
    public void retryFailedDeliveries() {

        logger.info("Retrying failed notifications...");

        List<NotificationDelivery> deliveries =
                notificationDeliveryRepository.findByNotificationDeliveryStatus(
                        NotificationDeliveryStatus.FAILED);

        processNotificationDeliveries(deliveries);
    }

    @Scheduled(fixedDelayString = "${notification.recovery.delay}")
    public void recoverStuckDeliveries() {

        logger.info("Recovering stuck notifications...");

        LocalDateTime cutoffTime = LocalDateTime.now().minus(processingTimeout);

        List<NotificationDelivery> deliveries =
                notificationDeliveryRepository
                        .findByNotificationDeliveryStatusAndUpdatedAtBefore(
                                NotificationDeliveryStatus.PROCESSING,
                                cutoffTime);

        recoverNotificationDeliveries(deliveries);
    }

    // ==========================
    // Helper Methods
    // ==========================

    private void processNotificationDeliveries(List<NotificationDelivery> deliveries) {

        deliveries.forEach(delivery ->
                notificationDeliveryService.processDelivery(delivery.getId()));
    }

    private void recoverNotificationDeliveries(List<NotificationDelivery> deliveries) {

        deliveries.forEach(delivery ->
                notificationDeliveryService.recoverStuckDelivery(delivery.getId()));
    }
}