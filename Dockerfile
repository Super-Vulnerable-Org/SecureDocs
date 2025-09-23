FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . /app

RUN apt-get update && apt-get install -y maven

RUN mvn clean package -DskipTests
RUN export KEY=AIzaSyAwW5MAaZnlKbS1Dco56phNfzyCdzHtKqs

EXPOSE 8080

CMD ["java", "-jar", "target/securedocs-0.0.1-SNAPSHOT.jar"]
