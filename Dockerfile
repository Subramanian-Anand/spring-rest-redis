FROM openjdk:alpine

MAINTAINER selva.kaushik@machinepulse.com

RUN java -version

VOLUME /tmp

COPY build/libs/spring-rest-redis-0.0.1-SNAPSHOT.jar /spring-rest-redis.jar
COPY src/main/resources/application.properties /application.properties

CMD ["java", "-jar", "-Dspring.config.location=/application.properties", "spring-rest-redis.jar"]