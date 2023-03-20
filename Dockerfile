#https://hub.docker.com/_/eclipse-temurin/tags
FROM eclipse-temurin:17-jdk-jammy
RUN mkdir /opt/app
COPY build/libs/peacerise-website-0.0.1-SNAPSHOT.jar /opt/app/bootapp.jar
CMD ["java", "-XX:+UseZGC", "-jar", "/opt/app/bootapp.jar"]