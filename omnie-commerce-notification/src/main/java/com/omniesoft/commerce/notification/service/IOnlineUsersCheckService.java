package com.omniesoft.commerce.notification.service;

import java.util.Set;

public interface IOnlineUsersCheckService {
    Set<String> filterOffline(Set<String> users);

    boolean isOnline(String userName);
}
