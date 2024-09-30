FROM eclipse-temurin:21

ARG JAR_FILE=build/libs/*.jar

WORKDIR /app

COPY ${JAR_FILE} app.jar

ARG SPRING_PROFILES_ACTIVE=prod
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

CMD ["java", "-Xmx2g", "-Xms2g", "-XX:+UseZGC", "-XX:+ZGenerational", "-jar", "app.jar"]
