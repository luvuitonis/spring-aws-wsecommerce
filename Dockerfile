FROM alpine/java:21-jdk
WORKDIR /app
COPY ./target/wsecommerce-1.0.0.jar /app
EXPOSE 8080
CMD ["java", "-jar", "wsecommerce-1.0.0.jar"]
