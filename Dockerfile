FROM openjdk:alpine

RUN java -version

COPY build/libs/spring-rest-redis-0.0.1-SNAPSHOT.jar /spring-rest-redis.jar

VOLUME /tmp

CMD ["java", "-jar", "spring-rest-redis.jar"]