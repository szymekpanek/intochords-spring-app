# Użycie lekkiej wersji Javy do uruchomienia aplikacji
FROM openjdk:17-jdk-slim

# Ustawienie katalogu roboczego w kontenerze
WORKDIR /app

# Kopiowanie pliku JAR do obrazu Dockera
COPY target/Intochords-spring-app-0.1.jar app.jar

# Wystawienie portu, na którym działa aplikacja
EXPOSE 8080

# Uruchomienie aplikacji
ENTRYPOINT ["java", "-jar", "app.jar"]
