FROM openjdk:8

COPY ./build/libs/beacon-account-manager.jar /usr/src/myapp/beacon-account-manager.jar
WORKDIR /usr/src/myapp