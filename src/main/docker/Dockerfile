FROM openjdk:8-jdk-alpine

RUN apk add --update \
    curl

COPY *.jar spring-boot-webapp.jar

EXPOSE 8888

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/spring-boot-webapp.jar", "--spring.config.additional-location=file:/etc/config/spring-config.yaml"]
