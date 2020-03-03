FROM openjdk:11-slim-buster

ENV LC_ALL=C

RUN apt-get update
RUN apt-get install locales tesseract-ocr libtesseract-dev -y

RUN sed -i -e 's/# ru_RU.UTF-8 UTF-8/ru_RU.UTF-8 UTF-8/' /etc/locale.gen && locale-gen

ENV LANG ru_RU.UTF-8  
ENV LANGUAGE ru_RU:ru

COPY eng.traineddata /tessdata/eng.traineddata
COPY application.yaml application.yaml
COPY comics-ocr-1.0-SNAPSHOT.jar comics-ocr.jar

CMD ["java", "-jar", "comics-ocr.jar", "--spring.config.location=file:./application.yaml"]