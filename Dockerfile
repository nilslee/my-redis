# Stage 1: Build
FROM maven:3.9.13-eclipse-temurin-25 AS build
WORKDIR /app
# 1. Copy ONLY the pom.xml
COPY pom.xml .
# 2. Download dependencies (this layer is cached until pom.xml changes)
RUN mvn dependency:go-offline -B
#Leverage caching by doing src copy last
COPY src ./src
# 4. Compile (fast, because dependencies are already in the image)
RUN mvn -q -B package
# 5. Debug
RUN cd target && ls

# Stage 2: Run
FROM maven:3.9.13-eclipse-temurin-25
WORKDIR /app
COPY --from=build /app/target/codecrafters-redis.jar /app/app.jar

EXPOSE 6379

ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]