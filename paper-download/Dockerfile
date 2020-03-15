FROM adoptopenjdk/openjdk11:alpine
COPY build/libs/comics-downloader-1.0-SNAPSHOT.jar comics-downloader.jar
COPY application-prod.yaml application.yaml
CMD ["java", "-jar", "comics-downloader.jar",  "--spring.config.location=file:./application.yaml"]
