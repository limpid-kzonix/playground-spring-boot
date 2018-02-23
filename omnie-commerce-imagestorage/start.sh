#!/bin/sh

printf 'Connecting ...'
until $(curl --output /dev/null --silent --head --fail http://omnie-gateway:9999/omnie-security/api/status); do
    printf '.'
    sleep 1
done

echo 'OmnieImageStorage service is starting... '
echo 'Try to connect to Eureka'
java $JAVA_OPTS -jar /app.jar $APP_OPTS