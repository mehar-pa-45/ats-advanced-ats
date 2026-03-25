# Stage 1: Build
FROM amazonlinux:2023 AS builder
RUN yum install -y java-21-amazon-corretto maven git

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM amazonlinux:2023
RUN yum install -y java-21-amazon-corretto
WORKDIR /app
COPY --from=builder /app/target/*.war app.war
EXPOSE 8080
CMD ["java", "-jar", "app.war"]
