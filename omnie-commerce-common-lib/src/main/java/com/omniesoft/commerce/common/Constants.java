package com.omniesoft.commerce.common;

public interface Constants {
    interface Role {
        String TYPE_OWNER = "owner";
        String TYPE_ADMIN = "admin";
    }

    interface Executors {
        String ASYNC_HTTP = "httpThreadPoolExecutor";
    }

    interface WS {
        interface User {
            String NOTIFICATION = "/user/notification";
        }

        interface Owner {
            String NOTIFICATION = "/owner/notification";
        }
    }
}
