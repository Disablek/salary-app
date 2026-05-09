FROM eclipse-temurin:21-jdk

# install curl and git for healthchecks and local git repo support
RUN apt-get update && apt-get install -y curl ca-certificates git && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]