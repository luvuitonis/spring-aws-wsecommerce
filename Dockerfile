FROM openjdk:25-ea-21-jdk-slim
WORKDIR /app
COPY ./target/wsecommerce-1.0.0.jar /app
EXPOSE 8080
CMD ["java", "-jar", "wsecommerce-1.0.0.jar"]
