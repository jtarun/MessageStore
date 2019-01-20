FROM anapsix/alpine-java
ADD target/messageStore-*-SNAPSHOT.jar /application.jar
CMD ["java","-jar","/application.jar"]