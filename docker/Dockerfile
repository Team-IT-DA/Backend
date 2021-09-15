FROM debian:stable

EXPOSE 3000

VOLUME /tmp

RUN apt update
RUN apt install -y openjdk-11-jdk
RUN java -version
RUN apt install -y git

RUN git clone https://github.com/Team-IT-DA/Backend.git

RUN Backend/./gradlew build

ENTRYPOINT ["java", "-jar", "Backend/build/libs/api-server-0.0.1-SNAPSHOT.jar"]