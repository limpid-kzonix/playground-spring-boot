#!/bin/sh

printf 'Connecting ...'
until $(curl --output /dev/null --silent --head --fail http://omnie-discovery:9998/status); do
    printf '.'
    sleep 1
done
echo 'OmnieGateway: Try to connect to Eureka'

java $JAVA_OPTS -jar /app.jar $APP_OPTS