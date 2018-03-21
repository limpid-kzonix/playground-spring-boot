
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
1 git clone omnie-commerce-parent
2 maven: clean package install
3 Run from IDEA: Always must be running: Discovery, Gateway, Security


### Start project from Docker
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
      - /Users/motokyi/development/omnie/logs/gateway/:/log
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
      - /Users/motokyi/development/omnie/logs/security/:/log
    extra_hosts:
      - "db:192.168.65.1"

#########################################
```

