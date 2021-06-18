FROM openjdk:17
 ADD target/accountservice-0.0.1-SNAPSHOT.jar AccountService.jar
 EXPOSE 8080
ENTRYPOINT ["java","-jar","AccountService.jar", "-Dspring.datasource.maxActive=1"]