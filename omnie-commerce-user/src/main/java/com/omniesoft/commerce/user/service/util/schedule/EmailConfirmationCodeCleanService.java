package com.omniesoft.commerce.user.service.util.schedule;

import com.omniesoft.commerce.persistence.repository.account.UserEmailVerificationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;

@Slf4j
@AllArgsConstructor
@Component
public class EmailConfirmationCodeCleanService {

    private final UserEmailVerificationRepository emailVerificationRepository;


    @Async(value = "scheduleThreadPoolExecutor")
    @Scheduled(fixedRate = 60000 * 60)
    public CompletableFuture<Void> cleanEmailTokens() {
        return CompletableFuture.runAsync(() -> {
            log.info("Clean table with old and not valid data of confirmation codes for email confirmation");
            emailVerificationRepository.deleteAllByCreateTimeBefore(LocalDateTime.now().minus(2, ChronoUnit.HOURS));
        });

    }
}
