FROM openjdk:8-jre-alpine

RUN apk add --update --no-cache tzdata && \
    ln -sf /usr/share/zoneinfo/Europe/Kiev /etc/localtime && \
    echo "Europe/Kiev" > /etc/timezone && \
    date && \
    rm -fr /tmp/* /var/cache/apk/*

ENV JAVA_OPTS="-server -Xmx1g -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap" \
    APP_OPTS="--spring.profiles.active=dev"

RUN apk add --no-cache curl

COPY target/omniecom-notification.jar  app.jar
COPY start.sh start.sh

RUN chmod +x start.sh start.sh

ENTRYPOINT exec ./start.sh