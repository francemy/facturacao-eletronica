FROM eclipse-temurin:17-jre

WORKDIR /app
COPY target/OKAERP-0.0.1.war app.war

EXPOSE 8833
ENTRYPOINT ["java", "-jar", "app.war"]
