FROM openjdk:8-jre-alpine

RUN apk add --no-cache curl

ENV JAVA_OPTS="-server -Xmx1g" \
    APP_OPTS="--spring.profiles.active=dev"

COPY target/omniecom-discovery.jar  app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar $APP_OPTS

