ROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . /app

RUN apt-get update && apt-get install -y maven
RUN export KEY=AIzaSyAwW5MAaZnlKbS1Dco56phNfzyCdzHtKqs
RUN mvn clean package -DskipTests

EXPOSE 8080
export KEY=AIzaSyAwW5MAaZnlKbS1Dco56phNfzyCdzHtKqs

CMD ["java", "-jar", "target/securedocs-0.0.1-SNAPSHOT.jar"]
