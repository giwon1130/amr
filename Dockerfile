# Build stage with JDK 17
FROM gradle:7.4.2-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Runtime stage with JDK 17
FROM openjdk:17-jdk
COPY --from=build /app/build/libs/amr.jar /amr.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/amr.jar"]
