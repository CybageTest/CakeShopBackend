# For Java 11, try this
FROM openjdk:17-jdk-alpine

# Refer to Maven build -> finalName
#ARG JAR_FILE=target\*.jar

#Expost port
EXPOSE 8091

ADD target/cakeshopfinal.jar cakeshopfinal.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","/cakeshopfinal.jar"]

# cd /opt/app
#WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
#COPY ${JAR_FILE} app.jar