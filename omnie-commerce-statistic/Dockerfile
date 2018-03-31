FROM openjdk:8-jre-alpine

RUN apk add --no-cache curl

ENV JAVA_OPTS="-server -Xmx1g -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap" \
    APP_OPTS="--spring.profiles.active=dev"

COPY target/omniecom-statistic.jar  app.jar
COPY start.sh start.sh

RUN chmod +x start.sh start.sh

ENTRYPOINT exec ./start.sh

