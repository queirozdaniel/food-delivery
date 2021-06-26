FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/*.jar food-delivery.jar
ENTRYPOINT ["java","-jar","/food-delivery.jar"]

