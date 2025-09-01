FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . /app

RUN apt-get update && apt-get install -y maven

RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/securedocs-0.0.1-SNAPSHOT.jar"]
RUN export KEY=AIzaSyAwW5MAaZnlKbS1Dco56phNfzyCdzHtKqs

mongodb://root:tiger@localhost:27017/admin?authSource=admin&directConnection=true
