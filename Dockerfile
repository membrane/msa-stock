FROM openjdk

COPY target/*.jar .

CMD ["java","-jar","stock-1.0.0.jar"]