FROM openjdk:8

COPY ./build/libs/agoda-downloader.jar /usr/src/myapp/agoda-downloader.jar
WORKDIR /usr/src/myapp