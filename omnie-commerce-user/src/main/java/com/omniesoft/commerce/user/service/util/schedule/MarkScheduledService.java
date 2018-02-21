package com.omniesoft.commerce.user.service.util.schedule;

import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import com.omniesoft.commerce.persistence.repository.service.ServiceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@AllArgsConstructor
public class MarkScheduledService {

    private final ServiceRepository serviceRepository;
    private final OrganizationRepository organizationRepository;

    @Async(value = "scheduleThreadPoolExecutor")
    @Scheduled(fixedRate = 60000 * 60 * 2)
    public CompletableFuture<Void> calculateServiceMark() {
        return CompletableFuture.runAsync(() -> {
            log.info("Calculate service marks");
            serviceRepository.calculateMarks();
        });
    }

    @Async(value = "scheduleThreadPoolExecutor")
    @Scheduled(fixedRate = 60000 * 60 * 2)
    public CompletableFuture<Void> calculateOrganizationMark() {
        return CompletableFuture.runAsync(() -> {
            log.info("Calculate organization marks");
            organizationRepository.calculateMarks();
        });
    }
}
