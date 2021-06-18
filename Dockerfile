FROM openjdk:17
 ADD target/accountservice-0.0.1-SNAPSHOT.jar AccountService.jar
 EXPOSE 8080
ENTRYPOINT ["java","-Dspring.datasource.hikari.maximum-pool-size=1","-jar","AccountService.jar"]
