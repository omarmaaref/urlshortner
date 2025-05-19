# build (skip tests)
FROM gradle:8.2-jdk17 AS builder
USER root
RUN mkdir -p /home/gradle/.gradle && chown -R gradle:gradle /home/gradle/.gradle
USER gradle
WORKDIR /home/gradle/project

COPY --chown=gradle:gradle . .
# skip tests with -x test
RUN ./gradlew clean build --no-daemon -x test --refresh-dependencies \
    && echo "Build succeeded (tests skipped)."

# runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
