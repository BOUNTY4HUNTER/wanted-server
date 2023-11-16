FROM ubuntu:22.04

RUN apt-get update && apt-get install -y openjdk-17-jre

WORKDIR /server
COPY ./ ./

EXPOSE 8080
CMD ./gradlew bootRun
