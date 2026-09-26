FROM gradle:9.4-jdk21-corretto AS builder
WORKDIR /usr/app
COPY build.gradle main.gradle settings.gradle gradle.properties lombok.config ./
COPY applications ./applications
COPY domain ./domain
COPY infrastructure ./infrastructure
RUN gradle bootJar --no-daemon --info --stacktrace

FROM eclipse-temurin:21-jdk-alpine
ENV APP_HOME=/usr/app
ENV JAR_FILE=iptv.jar
ENV JAVA_OPTS='-XX:+UseContainerSupport -XX:MaxRAMPercentage=70'
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR $APP_HOME

COPY --from=builder $APP_HOME/applications/app-service/build/libs/*.jar $JAR_FILE
USER appuser

EXPOSE 9000

ENTRYPOINT ["sh","-c", "java $JAVA_OPTS -jar $JAR_FILE"]