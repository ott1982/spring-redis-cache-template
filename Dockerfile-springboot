FROM maven:3-openjdk-11-slim as BUILDER
RUN mkdir ~/app
COPY ["pom.xml", "/root/app/"]
COPY ["src/", "/root/app/src/"]
RUN mvn -f /root/app/pom.xml verify

FROM openjdk:11-jre-slim-buster
COPY --from=BUILDER /root/app/target/*.jar /root/app/
ENTRYPOINT java -jar /root/app/*.jar