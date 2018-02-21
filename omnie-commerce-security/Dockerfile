FROM openjdk:8-jre-alpine

RUN apk add --no-cache curl

COPY target/omniecom-security.jar  app.jar
COPY start.sh start.sh

ENV JAVA_OPTS="-server -Xmx1g" \
    APP_OPTS="--spring.profiles.active=dev"



RUN chmod +x start.sh start.sh

ENTRYPOINT exec ./start.sh

