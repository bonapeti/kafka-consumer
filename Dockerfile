FROM openjdk:8

COPY target/consumer-*-uberjar.jar consumer-uberjar.jar
CMD ["java", "-jar","onsumer-uberjar.jar"]