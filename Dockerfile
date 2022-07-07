FROM adoptopenjdk:11-jre-hotspot
COPY target/task-scheduler-*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]