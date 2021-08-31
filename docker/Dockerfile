FROM openjdk:11-jdk

EXPOSE 8000

VOLUME /tmp

RUN git clone https://github.com/Team-IT-DA/Backend.git

RUN ls -a

RUN BackEnd/gradlew build

ENTRYPOINT ["java", "-jar", "BackEnd/build/libs/api-server-0.0.1-SNAPSHOT.jar"]