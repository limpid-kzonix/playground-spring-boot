#!/bin/sh
echo "Try to connect to the discovery service"
while [[ "$(curl -w ''%{http_code}'' http://omnie-discovery:9998/status)" != "200" ]] ; do sleep 50s; done
echo "Connected to the discovery service"
java $JAVA_OPTS -jar /app.jar $APP_OPTS