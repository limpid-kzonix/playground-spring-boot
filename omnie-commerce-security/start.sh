#!/bin/sh
echo "Try to connect to the gateway service"
printf 'Checking status of gateway-service '
printf 'Connecting ...'
until $(curl --output /dev/null --silent --head --fail http://omnie-gateway:9999/status); do
    printf '.'
    sleep 1
done
echo 'OmnieSecurity service is starting... '
echo 'Try to connect to Eureka'
java $JAVA_OPTS -jar /app.jar $APP_OPTS