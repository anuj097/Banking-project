FROM eclipse-temurin:17-jdk-focal
 
WORKDIR /app
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
 
EXPOSE 9191
COPY src ./src
 
CMD ["./mvnw", "spring-boot:run"]