FROM eclipse-temurin:8-jdk
WORKDIR /app

COPY target/fastjson_mysql-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]


