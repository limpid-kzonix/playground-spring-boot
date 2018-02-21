#!/bin/sh
echo "Try to connect to the gateway service"
while [["$(curl -w ''%{http_code}'' http://omnie-gateway:9999/status)" !=  "200" ]]; do sleep 50s; done
echo "Connected to the gateway service"
java $JAVA_OPTS -jar /app.jar $APP_OPTS