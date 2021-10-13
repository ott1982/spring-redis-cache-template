# Spring Redis Cache Template

Docker compose project based on 2 services: a CommandLineRunner Springboot (embeded H2) and a Standalone Redis instance.

The objective is insert a entity to H2 and load it twice; update the entity and load it again in order to show cached entity instead of modified register.

Everything by System.out.println.

**Technological stack: spring-boot-starter-cache, spring-boot-starter-data-redis, spring-boot-starter-data-jpa, H2 and Redis among others.**

# Requirements

* Port 6379 available.
* Docker compose.

# Execution

Follow next commands:

```sh
docker-compose build
docker-compose up -d
sleep(10)
docker-compose logs
docker-compose down
```

