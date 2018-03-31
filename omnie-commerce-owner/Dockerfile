FROM openjdk:8-jre-alpine

ENV JAVA_OPTS="-server -Xmx1g -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap" \
    APP_OPTS="--spring.profiles.active=dev"

RUN apk add --no-cache curl

COPY target/omniecom-owner.jar  app.jar
COPY start.sh start.sh

RUN chmod +x start.sh start.sh

ENTRYPOINT exec ./start.sh
