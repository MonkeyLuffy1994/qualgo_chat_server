FROM maven:3.8.4-openjdk-11 as maven

COPY . /tmp/

WORKDIR /tmp

RUN --mount=type=cache,target=/root/.m2  mvn clean package -Dmaven.test.skip

FROM openjdk:11

COPY --from=maven /tmp/target/qualgo_chat_server-1.0-SNAPSHOT.jar ./qualgo_chat_server-1.0-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "./qualgo_chat_server-1.0-SNAPSHOT.jar"]