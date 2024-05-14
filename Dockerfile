FROM openjdk:21-rc-oracle
MAINTAINER baizanis
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT ./application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","application.jar"]