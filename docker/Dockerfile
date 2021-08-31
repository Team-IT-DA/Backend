FROM debian:stable

EXPOSE 8000

VOLUME /tmp

RUN apt update
RUN apt install openjdk-11-jdk
RUN java -version

RUN git clone https://github.com/Team-IT-DA/Backend.git

RUN Backend/gradlew build

ENTRYPOINT ["java", "-jar", "Backend/build/libs/api-server-0.0.1-SNAPSHOT.jar"]