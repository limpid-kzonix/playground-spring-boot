package com.omniesoft.commerce.common;

public final class Constants {
    public class Role {
        public static final String TYPE_OWNER = "owner";
        public static final String TYPE_ADMIN = "admin";
    }

    public final class Executors {
        public static final String ASYNC_HTTP = "httpThreadPoolExecutor";
    }

    public final class WS {

        public final class User {
            public static final String NOTIFICATION = "/user/notification";
        }

        public final class Owner {
            public static final String NOTIFICATION = "/owner/notification";
        }
    }

    public final class URL {

        public final class User {
            public static final String ACCOUNT_CONFIRM = "/omnie-user/api/account/confirmation/";
            public static final String ACCOUNT_EMAIL_CHANGE = "/omnie-user/api/account/email/change/";
        }

        public final class Owner {

        }
    }
}
