FROM openjdk:8-jre-alpine

RUN apk add --update --no-cache tzdata && \
    ln -sf /usr/share/zoneinfo/Europe/Kiev /etc/localtime && \
    echo "Europe/Kiev" > /etc/timezone && \
    date && \
    rm -fr /tmp/* /var/cache/apk/*

RUN apk add --no-cache curl

ENV JAVA_OPTS="-server -Xmx1g -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap" \
    APP_OPTS="--spring.profiles.active=dev"

COPY target/omniecom-user.jar  app.jar
COPY start.sh start.sh
COPY /var/develop/apps/omni-141514-firebase-secrets.json omni-141514-firebase-secrets.json

RUN chmod +x start.sh start.sh

ENTRYPOINT exec ./start.sh

