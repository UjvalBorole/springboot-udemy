# Start from Java image
FROM openjdk:21

# Set working directory
WORKDIR /app

# Copy JAR file to container
COPY target/ecom-application-0.0.1-SNAPSHOT.jar /app/ecom-application-0.0.1-SNAPSHOT.jar

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "ecom-application-0.0.1-SNAPSHOT.jar"]


