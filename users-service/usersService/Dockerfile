FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD


COPY pom.xml /build/

COPY src /build/src/

WORKDIR /build

RUN mvn clean install -DskipTests

FROM java:8-jdk-alpine

WORKDIR /usr/app/

COPY --from=MAVEN_BUILD /build/target/usersService-0.0.1-SNAPSHOT.jar /usr/app/

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "usersService-0.0.1-SNAPSHOT.jar"]