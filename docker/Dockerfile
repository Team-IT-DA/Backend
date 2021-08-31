FROM openjdk:11-jdk

EXPOSE 8000

VOLUME /tmp

RUN git clone https://github.com/Team-IT-DA/Backend.git

RUN ls -a

RUN Backend/gradlew build

ENTRYPOINT ["java", "-jar", "Backend/build/libs/api-server-0.0.1-SNAPSHOT.jar"]