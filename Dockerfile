FROM openjdk

COPY target/*.jar .

CMD ["java","-jar","stock-0.0.1-SNAPSHOT.jar"]