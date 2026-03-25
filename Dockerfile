# Stage 1: Build
FROM amazonlinux:2023 AS builder
RUN yum install -y java-21-amazon-corretto maven git
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

# Stage 2: Runtime
FROM amazonlinux:2023
RUN yum install -y java-21-amazon-corretto
WORKDIR /app
COPY --from=builder /app/target/*.war app.war
EXPOSE 8080
CMD ["java", "-jar", "app.war"]
