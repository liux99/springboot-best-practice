FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
RUN mkdir -p /app/newrelic

RUN wget -q -O /app/newrelic/newrelic.jar https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic.jar
COPY newrelic/newrelic.yml /app/newrelic/
 
ENTRYPOINT ["java","-javaagent:/app/newrelic/newrelic.jar","-jar","app.jar"]
