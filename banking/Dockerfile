FROM eclipse-temurin:17-jdk-focal
 
WORKDIR /app
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
 
EXPOSE 9090
COPY src ./src
 
CMD ["./mvnw", "spring-boot:run"]