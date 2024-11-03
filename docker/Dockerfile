FROM tomcat:10.1.25-jdk17-temurin-jammy
WORKDIR /app

COPY target/job-offer-producer.jar /app/job-offer-producer.jar

# Expose the Tomcat port (default 8080)
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "job-offer-producer.jar"]