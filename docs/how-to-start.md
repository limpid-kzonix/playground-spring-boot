
### Software
- Git
- Postgres 9.6
- Maven 
- MongoDB
- Redis
- IDEA
- Docker


### IDEA Setup  
- IntelliJ IDEA Ultimate
- IntelliJ Lombok plugin + Enable Annotation Processing


### Start project from IDEA
- git clone omnie-commerce-parent, target branch `develop`
- create postgres db `omnie_commerce` make sure  db contains schema `public`
- add maven `settings-security.xml` & `settings.xml` by this guide: https://maven.apache.org/guides/mini/guide-encryption.html
- for generating local db tables by liquibase `settings.xml` must contain:

  ```xml 
  
    <settings>
      <servers>
        <server>
          <id>server.database</id>
          <username>postgres</username>
          <password>{ENCRYPTED-DB-PASSWORD}</password>
        </server>
      </servers>
    </settings>

  ```
- maven build from poject root: `clean package install`
- Run from IDEA: Always must be running: Discovery, Gateway, Security


### Start project from Docker
docker-compose.yml example:

```yaml

version: '3.3'

services:
#########################################
  omnie-discovery:
    build: ./omnie-commerce-parent/omnie-commerce-discovery/
    image: omnie_discovery
    ports:
      - "9998:9998"
    environment:
      - JAVA_OPTS=-server -Xmx1g
      - APP_OPTS=--spring.profiles.active=dev
    volumes:
      - /omnie/logs/discovery/:/log
#########################################
  omnie-gateway:
    build: ./omnie-commerce-parent/omnie-commerce-gateway/
    image: omnie_gateway
    ports:
      - "9999:9999"
    environment:
      - JAVA_OPTS=-server -Xmx1g
      - APP_OPTS=--spring.profiles.active=dev --spring.redis.password=cISePhIrChOrMACkeTICiAhECludeWha
    volumes:
      - /omnie/logs/gateway/:/log
    extra_hosts:
      - "redis:172.18.0.1"
#########################################
  omnie-security:
    build: ./omnie-commerce-parent/omnie-commerce-security/
    image: omnie_security
    ports:
      - "9010:9010"
    environment:
      - JAVA_OPTS=-server -Xmx1g
      - APP_OPTS=--spring.profiles.active=dev --spring.datasource.password=geek --spring.datasource.user=postgres --spring.datasource.url=jdbc:postgresql://192.168.65.1:5432/omnie_commerce
    volumes:
      - /omnie/logs/security/:/log
    extra_hosts:
      - "db:192.168.65.1"

#########################################
```

