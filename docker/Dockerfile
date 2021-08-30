FROM ubuntu:16.04

EXPOSE 8080

RUN apt-get update

RUN apt-get install -y default-jdk

ARG JAR_FILE=build/lib/api-server-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} itda.jar

ENTRYPOINT ["java", "-jar", "/itda.jar"]