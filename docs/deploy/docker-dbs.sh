#!/bin/sh

docker network create omnie-bridge

echo '#######################################################################################################################'

echo 'Postgres setup ...'
docker stop postgres
docker container rm postgres
echo 'https://docs.docker.com/samples/library/postgres/'
docker pull postgres:9.6.8-alpine
docker run -d --name postgres --restart="always" -p 5432:5432 --env-file /var/prod/secrets/pg.env -v /var/prod/data/postgres:/var/lib/postgresql/data postgres:9.6.8-alpine

echo 'Create new db folowing next steps:'
echo '1. psql -U postgres'
echo '2. CREATE DATABASE omnie_commerce;'
echo '3. \q'
echo '4. exit'
docker exec -it postgres bash
docker network connect omnie-bridge postgres
echo 'Docker:: Postgres setup done'

echo '#######################################################################################################################'

echo 'Docker:: MongoDB setup ...'
docker stop mongo
docker container rm mongo
echo 'https://docs.docker.com/samples/library/mongo/'
docker pull mongo:3.6.3-jessie
docker run -d --name mongo --restart="always" -v/var/prod/data/mongo:/data/db mongo:3.6.3-jessie
docker network connect omnie-bridge mongo
echo 'Docker:: MongoDB setup done'

echo '#######################################################################################################################'

echo 'Docker:: Redis setup ...'
docker stop redis
docker container rm redis
echo 'https://docs.docker.com/samples/library/redis/'
docker pull redis:4.0-alpine
docker run -d --name redis -m="2048m" --cpus="2" --restart="always" -p 6379:6379 -v /var/prod/secrets/redis.conf:/usr/local/etc/redis/redis.conf redis:4.0-alpine redis-server /usr/local/etc/redis/redis.conf
docker network connect omnie-bridge redis
echo 'Docker:: Redis setup done'

echo '#######################################################################################################################'