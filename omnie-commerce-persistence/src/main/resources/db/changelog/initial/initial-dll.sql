CREATE SCHEMA IF NOT EXISTS public;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
------------------------------------------------------------------------------------------------------------------------
----------------------------------------- CATEGORIES -------------------------------------------------------------------
CREATE TABLE category (
  uuid        UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  eng_name    VARCHAR(200) NOT NULL,
  rus_name    VARCHAR(200) NOT NULL,
  ukr_name    VARCHAR(200) NOT NULL,
  logo_id     VARCHAR(128)          DEFAULT NULL,
  create_time TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE sub_category (
  uuid        UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  category_id UUID         NOT NULL REFERENCES category (uuid) ON DELETE CASCADE,
  eng_name    VARCHAR(200) NOT NULL,
  rus_name    VARCHAR(200) NOT NULL,
  ukr_name    VARCHAR(200) NOT NULL,
  create_time TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE language (
  uuid UUID        NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  name VARCHAR(10) NOT NULL UNIQUE
);
------------------------------------------------------------------------------------------------------------------------
------------------------------------------ ACCOUNT ---------------------------------------------------------------------
CREATE TABLE user_setting (
  uuid                       UUID      NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  event_notification         BOOLEAN   NOT NULL DEFAULT TRUE,
  push_notification          BOOLEAN   NOT NULL DEFAULT TRUE,
  sound_notification         BOOLEAN   NOT NULL DEFAULT FALSE,
  background_notification    BOOLEAN   NOT NULL DEFAULT FALSE,
  notification_delay_minutes INTEGER   NOT NULL DEFAULT 15, -- minutes
  calendar_sync              BOOLEAN   NOT NULL DEFAULT FALSE,
  light_theme                BOOLEAN   NOT NULL DEFAULT FALSE,
  create_time                TIMESTAMP NOT NULL DEFAULT now()
);
CREATE TABLE user_profile (
  uuid       UUID        NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  first_name VARCHAR(128)         DEFAULT NULL,
  last_name  VARCHAR(128)         DEFAULT NULL,
  birthday   DATE                 DEFAULT NULL,
  image_id   VARCHAR(128)         DEFAULT NULL,
  phone      VARCHAR(20)          DEFAULT NULL CHECK (phone ~
                                                      '(^(\+\d{1,5})?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{2}[\s.-]\d{2}$)|(\+[\d\s]{7,20})$'),
  gender     INT2                 DEFAULT 0, -- 0=man 1= woman
  omnie_card VARCHAR(60) NOT NULL
);

CREATE TABLE users (
  uuid              UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  login             VARCHAR(50)  NOT NULL UNIQUE CHECK (login ~* '^(?=.*[a-z0-9]$)([a-z0-9][_.]?){3,50}$'),
  email             VARCHAR(100) NOT NULL UNIQUE CHECK (email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'),
  encrypt_password  VARCHAR(100) NOT NULL,
  access_token      VARCHAR(100)          DEFAULT NULL,
  salt              VARCHAR(100) NOT NULL,
  registration_date TIMESTAMP    NOT NULL DEFAULT now(),
  profile_id        UUID                  DEFAULT NULL REFERENCES user_profile (uuid) ON DELETE SET NULL,
  user_setting_id   UUID                  DEFAULT NULL REFERENCES user_setting (uuid) ON DELETE SET NULL
);

CREATE TABLE user_password_reset (
  uuid        UUID      NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  user_id     UUID      NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  code        INTEGER   NOT NULL,
  reset_token VARCHAR(200)       DEFAULT NULL,
  expire_time TIMESTAMP NOT NULL,
  create_time TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE user_email_verification (
  uuid           UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  user_id        UUID         NOT NULL UNIQUE  REFERENCES users (uuid) ON DELETE CASCADE,
  token          VARCHAR(200) NOT NULL UNIQUE,
  confirm_status BOOLEAN               DEFAULT FALSE,
  create_time    TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE user_oauth (
  uuid         UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
  user_id      UUID             NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  oauth_client INT              NOT NULL,
  profile_id   TEXT             NOT NULL UNIQUE,
  UNIQUE (oauth_client, user_id)
);

CREATE TABLE owner (
  uuid          UUID      NOT NULL          DEFAULT uuid_generate_v4() PRIMARY KEY,
  user_id       UUID      NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  active_status BOOLEAN   NOT NULL          DEFAULT TRUE,
  create_time   TIMESTAMP NOT NULL          DEFAULT now()
);

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE handbook (
  uuid          UUID         NOT NULL  DEFAULT uuid_generate_v4() PRIMARY KEY,
  name          VARCHAR(256) NOT NULL,
  address       TEXT         NOT NULL,
  image_id      VARCHAR(128)           DEFAULT NULL,
  accept_status BOOLEAN                DEFAULT FALSE
);
CREATE UNIQUE INDEX handbook_name_index
  ON handbook (name);

CREATE TABLE handbook_phone (
  uuid        UUID        NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  handbook_id UUID        NOT NULL REFERENCES handbook (uuid) ON DELETE CASCADE,
  phone       VARCHAR(24) NOT NULL
);

CREATE TABLE handbook_tag (
  uuid        UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  handbook_id UUID         NOT NULL REFERENCES handbook (uuid) ON DELETE CASCADE,
  tag         VARCHAR(100) NOT NULL
);

CREATE TABLE user_handbook (
  user_id     UUID NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  handbook_id UUID NOT NULL REFERENCES handbook (uuid) ON DELETE CASCADE,
  PRIMARY KEY (user_id, handbook_id)
);
------------------------------------------------------------------------------------------------------------------------
------------------------  ORGANIZATION  --------------------------------------------------------------------------------
CREATE TABLE organization_setting (
  uuid          UUID    NOT NULL  DEFAULT uuid_generate_v4() PRIMARY KEY,
  freeze_status BOOLEAN NOT NULL  DEFAULT FALSE,
  delete_status BOOLEAN NOT NULL  DEFAULT FALSE,
  reason        TEXT              DEFAULT NULL
);

CREATE TABLE organization (
  uuid                UUID         NOT NULL  DEFAULT uuid_generate_v4() PRIMARY KEY,
  owner_id            UUID         NOT NULL REFERENCES owner (uuid) ON DELETE CASCADE,
  name                VARCHAR(100) NOT NULL,
  email               VARCHAR(100) NOT NULL CHECK ( email ~ '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$' ),
  title               TEXT                   DEFAULT NULL,
  description         TEXT                   DEFAULT NULL,
  logo_id             TEXT                   DEFAULT NULL,
  background_image_id VARCHAR(128)           DEFAULT NULL,
  placeid             TEXT         NOT NULL,
  longitude           DOUBLE PRECISION       DEFAULT NULL,
  latitude            DOUBLE PRECISION       DEFAULT NULL,
  setting_id          UUID REFERENCES organization_setting (uuid) ON DELETE SET NULL,
  create_time         TIMESTAMP    NOT NULL  DEFAULT now()
);

CREATE TABLE organization_gallery (
  uuid            UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  organization_id UUID         NOT NULL REFERENCES organization (uuid) ON DELETE CASCADE,
  image_id        VARCHAR(128) NOT NULL
);

CREATE TABLE organization_phone (
  uuid            UUID        NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  organization_id UUID        NOT NULL REFERENCES organization (uuid) ON DELETE CASCADE,
  phone           VARCHAR(32) NOT NULL CHECK (phone ~
                                              '(^(\+\d{1,5})?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{2}[\s.-]\d{2}$)|(\+[\d\s]{7,20})$')
);

CREATE TABLE organization_timesheet (
  uuid            UUID    NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  organization_id UUID    NOT NULL REFERENCES organization (uuid) ON DELETE CASCADE,
  day             INTEGER NOT NULL,
  work_start      TIME    NOT NULL,
  work_end        TIME    NOT NULL,
  break_start     TIME             DEFAULT NULL,
  break_end       TIME             DEFAULT NULL
);
------------------------------------------------------------------------------------------------------------------------
-------- CARDS ---------------------------------------------------------------------------------------------------------
CREATE TABLE card_discount (
  uuid        UUID         NOT NULL  DEFAULT uuid_generate_v4() PRIMARY KEY,
  user_id     UUID         NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  image       VARCHAR(128)           DEFAULT NULL,
  name        VARCHAR(512) NOT NULL,
  code        VARCHAR(256) NOT NULL,
  format      VARCHAR(32)  NOT NULL,
  create_time TIMESTAMP    NOT NULL  DEFAULT now()
);

CREATE TABLE card_business (
  uuid        UUID         NOT NULL  DEFAULT uuid_generate_v4() PRIMARY KEY,
  user_id     UUID         NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  image       VARCHAR(128)           DEFAULT NULL,
  name        VARCHAR(300) NOT NULL,
  email       VARCHAR(300)           DEFAULT NULL,
  phone       TEXT                   DEFAULT NULL,
  comment     TEXT                   DEFAULT NULL,
  create_time TIMESTAMP    NOT NULL  DEFAULT now()
);
------------------------------------------------------------------------------------------------------------------------
------------------------------------SERVICE-----------------------------------------------------------------------------

CREATE TABLE service_setting (
  uuid          UUID    NOT NULL  DEFAULT uuid_generate_v4() PRIMARY KEY,
  freeze_status BOOLEAN NOT NULL  DEFAULT FALSE,
  delete_status BOOLEAN NOT NULL  DEFAULT FALSE,
  reason        TEXT              DEFAULT NULL
);

CREATE TABLE service (
  uuid            UUID         NOT NULL  DEFAULT uuid_generate_v4() PRIMARY KEY,
  name            VARCHAR(200) NOT NULL,
  description     TEXT                   DEFAULT NULL,
  organization_id UUID         NOT NULL REFERENCES organization (uuid) ON DELETE CASCADE,
  setting_id      UUID         NOT NULL REFERENCES service_setting (uuid) ON DELETE CASCADE,
  logo_id         VARCHAR(128)           DEFAULT NULL,
  create_time     TIMESTAMP    NOT NULL  DEFAULT now(),
  create_by       UUID                   DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL
);

CREATE TABLE service_gallery (
  uuid       UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  service_id UUID         NOT NULL REFERENCES service (UUID) ON DELETE CASCADE,
  image_id   VARCHAR(128) NOT NULL
);

CREATE TABLE service_price (
  uuid        UUID             NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  service_id  UUID             NOT NULL REFERENCES service (uuid) ON DELETE CASCADE,
  day_mask    INTEGER          NOT NULL,
  start_rule  TIME             NOT NULL,
  end_rule    TIME             NOT NULL,
  price       DOUBLE PRECISION NOT NULL,
  price_unit  INTEGER          NOT NULL DEFAULT 1, -- 1=grn/hr; 1=grn/min; 2=grn/sec;
  expense     DOUBLE PRECISION NOT NULL DEFAULT 0,
  active_from TIMESTAMP        NOT NULL,
  create_time TIMESTAMP        NOT NULL DEFAULT now(),
  update_by   UUID                      DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL
);

CREATE TABLE service_timing (
  uuid            UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
  service_id      UUID                            NOT NULL    REFERENCES service (uuid) ON DELETE CASCADE,
  min_duration    BOOLEAN                         NOT NULL,
  duration_millis BIGINT                          NOT NULL,
  active_from     TIMESTAMP                       NOT NULL,
  pause_millis    BIGINT DEFAULT 0                NOT NULL,
  slot_count      INTEGER DEFAULT 1               NOT NULL,
  create_time     TIMESTAMP                       NOT NULL DEFAULT now(),
  update_by       UUID                                     DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL
);

CREATE TABLE service_category (
  service_id      UUID NOT NULL REFERENCES service (uuid) ON DELETE CASCADE,
  sub_category_id UUID NULL REFERENCES sub_category (uuid) ON DELETE CASCADE,
  PRIMARY KEY (service_id, sub_category_id)
);

CREATE TABLE sub_service (
  uuid        UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  name        VARCHAR(200) NOT NULL,
  service_id  UUID         NOT NULL REFERENCES service (uuid) ON DELETE CASCADE,
  create_time TIMESTAMP    NOT NULL DEFAULT now(),
  create_by   UUID                  DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL
);
CREATE TABLE sub_service_price (
  uuid             UUID             NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  measurement_unit INTEGER          NULL     DEFAULT 0, -- 0=unit;
  sub_service_id   UUID             NOT NULL REFERENCES sub_service (uuid) ON DELETE CASCADE,
  active_from      TIMESTAMP        NOT NULL,
  duration_millis  BIGINT                    DEFAULT 0,
  expense          DOUBLE PRECISION NOT NULL DEFAULT 0,
  price            DOUBLE PRECISION NOT NULL DEFAULT 0,
  min_count        INTEGER          NOT NULL DEFAULT 1,
  max_count        INTEGER          NOT NULL DEFAULT 1,
  max_discount     DOUBLE PRECISION NOT NULL DEFAULT 0,
  update_by        UUID                      DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL
);

CREATE TABLE service_language (
  service_id  UUID NOT NULL REFERENCES service (uuid) ON DELETE CASCADE,
  language_id UUID NOT NULL REFERENCES language (uuid) ON DELETE CASCADE,
  PRIMARY KEY (service_id, language_id)
);
------------------------------------------------------------------------------------------------------------------------
---------------------------------- BLACKLIST ---------------------------------------------------------------------------
CREATE TABLE black_list (
  uuid            UUID      NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  user_id         UUID      NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  organization_id UUID      NOT NULL REFERENCES organization (uuid) ON DELETE CASCADE,
  comment         TEXT               DEFAULT NULL,
  create_time     TIMESTAMP NOT NULL DEFAULT now(),
  create_by       UUID               DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL
);
------------------------------------------------------------------------------------------------------------------------
---------------------------------- CUSTOMERS ---------------------------------------------------------------------------
CREATE TABLE customers_group (
  uuid            UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  name            VARCHAR(100) NOT NULL,
  organization_id UUID         NOT NULL REFERENCES organization (uuid) ON DELETE CASCADE,
  create_time     TIMESTAMP    NOT NULL DEFAULT now(),
  create_by       UUID                  DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL
);

CREATE TABLE customers_group_to_user (
  group_id UUID NOT NULL REFERENCES customers_group (uuid) ON DELETE CASCADE,
  user_id  UUID NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  PRIMARY KEY (group_id, user_id)
);
------------------------------------------------------------------------------------------------------------------------
--------------------------------- PERMISSIONS IN PERSONNEL MANAGEMENT --------------------------------------------------

CREATE TABLE admin_role (
  uuid            UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  organization_id UUID         NOT NULL REFERENCES organization (uuid) ON DELETE CASCADE,
  name            VARCHAR(100) NOT NULL
);

CREATE TABLE admin_permission (
  uuid UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

CREATE TABLE admin_role_to_permission (
  role_id       UUID NOT NULL REFERENCES admin_role (uuid) ON DELETE CASCADE,
  permission_id UUID DEFAULT NULL REFERENCES admin_permission (uuid) ON DELETE CASCADE,
  PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE admin (
  uuid    UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  user_id UUID NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  role_id UUID NOT NULL REFERENCES admin_role (uuid) ON DELETE CASCADE,
  UNIQUE (user_id, role_id)
);

CREATE TABLE admin_to_service (
  admin_id   UUID NOT NULL REFERENCES admin (uuid) ON DELETE CASCADE,
  service_id UUID NOT NULL REFERENCES service (uuid) ON DELETE CASCADE,
  PRIMARY KEY (admin_id, service_id)
);
------------------------------------------------------------------------------------------------------------------------
---------- NEWS --------------------------------------------------------------------------------------------------------
CREATE TABLE news (
  uuid             UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  organization_id  UUID         NOT NULL REFERENCES organization (uuid) ON DELETE CASCADE,
  image_id         VARCHAR(128)          DEFAULT NULL,
  title            VARCHAR(255) NOT NULL,
  text             TEXT         NOT NULL,
  promotion_status BOOLEAN               DEFAULT FALSE,
  post_time        TIMESTAMP    NOT NULL DEFAULT now(),
  create_time      TIMESTAMP    NOT NULL DEFAULT now(),
  create_by        UUID                  DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL
);

CREATE TABLE news_to_service (
  news_id    UUID NOT NULL REFERENCES news (uuid) ON DELETE CASCADE,
  service_id UUID NOT NULL REFERENCES service (uuid) ON DELETE CASCADE,
  PRIMARY KEY (news_id, service_id)
);
------------------------------------------------------------------------------------------------------------------------
----------------------------------------------- FAVORITE ---------------------------------------------------------------
CREATE TABLE favorite_service (
  user_id    UUID NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  service_id UUID NOT NULL REFERENCES service (uuid) ON DELETE CASCADE,
  PRIMARY KEY (user_id, service_id)
);

CREATE TABLE favorite_organization (
  user_id         UUID NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  organization_id UUID NOT NULL REFERENCES organization (uuid) ON DELETE CASCADE,
  PRIMARY KEY (user_id, organization_id)
);

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------- MARK -----------------------------------------------------------------
CREATE TABLE mark_organization (
  uuid            UUID             NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  organization_id UUID             NOT NULL UNIQUE REFERENCES organization (uuid) ON DELETE CASCADE,
  rating          DOUBLE PRECISION NOT NULL DEFAULT 0.0
);

CREATE TABLE mark_service (
  uuid       UUID             NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  service_id UUID             NOT NULL UNIQUE REFERENCES service (uuid) ON DELETE CASCADE,
  rating     DOUBLE PRECISION NOT NULL DEFAULT 0.0
);

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------ REVIEW ----------------------------------------------------------------
CREATE TABLE review_service (
  uuid          UUID      NOT NULL         DEFAULT uuid_generate_v4() PRIMARY KEY,
  user_id       UUID      NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  mark          INT                        DEFAULT 0 CHECK (mark >= 0 AND mark <= 5),
  service_id    UUID      NOT NULL REFERENCES service (uuid) ON DELETE CASCADE,
  text          TEXT                       DEFAULT NULL,
  accept_status BOOLEAN   NOT NULL         DEFAULT FALSE,
  create_time   TIMESTAMP NOT NULL         DEFAULT now(),
  update_time   TIMESTAMP                  DEFAULT NULL,
  UNIQUE (user_id, service_id)
);

CREATE TABLE review_organization (
  uuid            UUID      NOT NULL         DEFAULT uuid_generate_v4() PRIMARY KEY,
  user_id         UUID      NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  mark            INT                        DEFAULT 0 CHECK (mark >= 0 AND mark <= 5),
  organization_id UUID      NOT NULL REFERENCES organization (uuid) ON DELETE CASCADE,
  text            TEXT                       DEFAULT NULL,
  accept_status   BOOLEAN   NOT NULL         DEFAULT FALSE,
  create_time     TIMESTAMP NOT NULL         DEFAULT now(),
  update_time     TIMESTAMP                  DEFAULT NULL,
  UNIQUE (user_id, organization_id)

);
------------------------------------------------------------------------------------------------------------------------
----------------------------------------------- DISCOUNT ---------------------------------------------------------------

CREATE TABLE discount (
  uuid            UUID             NOT NULL             DEFAULT uuid_generate_v4() PRIMARY KEY,
  name            VARCHAR(200)                          DEFAULT NULL,
  organization_id UUID             NOT NULL REFERENCES organization (uuid) ON DELETE CASCADE,
  start_time      TIMESTAMP        NOT NULL,
  end_time        TIMESTAMP        NOT NULL,
  percent         DOUBLE PRECISION NOT NULL             DEFAULT 0.0 CHECK (percent >= 0 AND percent <= 100),
  personal_status BOOLEAN          NOT NULL             DEFAULT FALSE,
  create_time     TIMESTAMP        NOT NULL             DEFAULT now(),
  update_by       UUID                                  DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL
);

CREATE TABLE discount_for_service (
  service_id  UUID NOT NULL REFERENCES service (uuid) ON DELETE CASCADE,
  discount_id UUID NOT NULL REFERENCES discount (uuid) ON DELETE CASCADE,
  PRIMARY KEY (service_id, discount_id)
);

CREATE TABLE discount_for_sub_service (
  sub_service_id UUID NOT NULL REFERENCES sub_service (uuid) ON DELETE CASCADE,
  discount_id    UUID NOT NULL REFERENCES discount (uuid) ON DELETE CASCADE,
  PRIMARY KEY (sub_service_id, discount_id)
);

CREATE TABLE discount_for_user (
  user_id     UUID NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  discount_id UUID NOT NULL REFERENCES discount (uuid) ON DELETE CASCADE,
  PRIMARY KEY (user_id, discount_id)
);
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------- ORDERS ---------------------------------------------------------------
CREATE TABLE orders (
  uuid             UUID             NOT NULL           DEFAULT uuid_generate_v4() PRIMARY KEY,
  user_id          UUID                                DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL,
  number           SERIAL           NOT NULL,
  service_id       UUID             NOT NULL REFERENCES service (uuid) ON DELETE CASCADE,
  status           VARCHAR(32)      NOT NULL,
  start_time       TIMESTAMP        NOT NULL,
  end_time         TIMESTAMP        NOT NULL,
  customer_name    VARCHAR(128)     NOT NULL,
  customer_phone   VARCHAR(32)      NOT NULL,
  comment          TEXT                                DEFAULT NULL,
  discount_id      UUID                                DEFAULT NULL REFERENCES discount (uuid) ON DELETE SET NULL,
  discount_percent DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  service_price    DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  service_expense  DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  total_price      DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  create_time      TIMESTAMP        NOT NULL           DEFAULT now(),
  update_time      TIMESTAMP                           DEFAULT NULL,
  updated_by       UUID                                DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL,
  done_by          UUID                                DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL
);

CREATE TABLE order_to_sub_service (
  uuid                UUID             NOT NULL           DEFAULT uuid_generate_v4() PRIMARY KEY,
  order_id            UUID             NOT NULL REFERENCES orders (uuid) ON DELETE CASCADE,
  sub_service_id      UUID                                DEFAULT NULL REFERENCES sub_service (uuid) ON DELETE SET NULL,
  discount_id         UUID                                DEFAULT NULL REFERENCES discount (uuid) ON DELETE SET NULL,
  count               INTEGER          NOT NULL           DEFAULT 1,
  discount_percent    DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  sub_service_price   DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  sub_service_expense DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  total_price         DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  UNIQUE (order_id, sub_service_id)
);

-- ORDER TEMPLATES

CREATE TABLE template_order (
  user_id  UUID NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  order_id UUID NOT NULL REFERENCES orders (uuid) ON DELETE CASCADE,
  PRIMARY KEY (user_id, order_id)
);

-- ORDERS STORY

CREATE TABLE order_story (
  uuid             UUID             NOT NULL           DEFAULT uuid_generate_v4() PRIMARY KEY,
  order_id         UUID                                DEFAULT NULL REFERENCES orders (uuid) ON DELETE SET NULL,
  user_id          UUID             NOT NULL REFERENCES users (uuid) ON DELETE CASCADE,
  service_id       UUID                                DEFAULT NULL REFERENCES service (uuid) ON DELETE SET NULL,
  status           VARCHAR(32)      NOT NULL,
  start_time       TIMESTAMP        NOT NULL,
  end_time         TIMESTAMP        NOT NULL,
  comment          TEXT                                DEFAULT NULL,
  discount_id      UUID                                DEFAULT NULL REFERENCES discount (uuid) ON DELETE SET NULL,
  discount_percent DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  service_price    DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  total_price      DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  create_time      TIMESTAMP        NOT NULL           DEFAULT now(),
  change_by        UUID                                DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL
);

CREATE TABLE order_sub_service_story (
  uuid             UUID             NOT NULL           DEFAULT uuid_generate_v4() PRIMARY KEY,
  order_story_id   UUID             NOT NULL REFERENCES order_story (uuid) ON DELETE CASCADE,
  sub_service_id   UUID                                DEFAULT NULL REFERENCES sub_service (uuid) ON DELETE SET NULL,
  discount_id      UUID                                DEFAULT NULL REFERENCES discount (uuid) ON DELETE SET NULL,
  count            INTEGER          NOT NULL           DEFAULT 1,
  discount_percent DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  service_price    DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  total_price      DOUBLE PRECISION NOT NULL           DEFAULT 0.0,
  UNIQUE (order_story_id, sub_service_id)
);
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE conversation (
  uuid            UUID      NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  organization_id UUID      NOT NULL REFERENCES organization (uuid) ON DELETE CASCADE,
  user_id         UUID               DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL,
  create_time     TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE message (
  uuid            UUID      NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
  message         TEXT      NOT NULL,
  conversation_id UUID      NOT NULL REFERENCES conversation (uuid) ON DELETE CASCADE,
  sender_id       UUID               DEFAULT NULL REFERENCES users (uuid) ON DELETE SET NULL,
  create_time     TIMESTAMP NOT NULL DEFAULT now(),
  read_status     BOOLEAN   NOT NULL DEFAULT FALSE
);
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------