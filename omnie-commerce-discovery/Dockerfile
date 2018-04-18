FROM openjdk:8-jre-alpine

RUN apk add --update --no-cache tzdata && \
    ln -sf /usr/share/zoneinfo/Europe/Kiev /etc/localtime && \
    echo "Europe/Kiev" > /etc/timezone && \
    date && \
    rm -fr /tmp/* /var/cache/apk/*

RUN apk add --no-cache curl

ENV JAVA_OPTS="-server -Xmx1g -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap" \
    APP_OPTS="--spring.profiles.active=dev"

COPY target/omniecom-discovery.jar  app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar $APP_OPTS

