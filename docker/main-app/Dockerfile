FROM adoptopenjdk/openjdk11:alpine
ENV LC_ALL=C

RUN apk update
RUN apk add tesseract-ocr tesseract-ocr-dev

COPY comics-ocr-1.0-SNAPSHOT.jar comics-ocr.jar
COPY eng.traineddata /tessdata/eng.traineddata

CMD ["java", "-jar", "comics-ocr.jar"]