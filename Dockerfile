FROM adoptopenjdk/openjdk11
EXPOSE 3000
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} itda-server.jar
ENTRYPOINT ["java", "-jar", "itda-server.jar"]
