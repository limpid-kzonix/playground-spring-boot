package com.omniesoft.commerce.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnlineUsersCheckService implements IOnlineUsersCheckService {
    private final SimpUserRegistry registry;

    @Override
    public Set<String> filterOffline(Set<String> users) {
        return users.stream()
                .filter(this::hasSession)
                .collect(Collectors.toSet());

    }

    @Override
    public boolean isOnline(String userName) {
        return hasSession(userName);
    }


    private boolean hasSession(String userName) {
        SimpUser user = registry.getUser(userName);
        if (user != null) {
            return user.hasSessions();
        }
        return false;
    }
}
