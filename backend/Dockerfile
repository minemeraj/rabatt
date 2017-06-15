FROM maven:3.5.0-jdk-8-alpine

RUN mkdir /code
WORKDIR /code

COPY . /code
RUN mvn clean install
