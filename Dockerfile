FROM openjdk
COPY target/relearnspring-0.0.1-SNAPSHOT.jar /home/
ENTRYPOINT ["java", "-jar", "/home/relearnspring-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081