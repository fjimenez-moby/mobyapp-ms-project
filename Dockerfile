# Multi-stage build para Project Service con GitHub Packages

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Recibir credenciales de GitHub como build arguments
ARG GITHUB_TOKEN
ARG GITHUB_USERNAME

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Verificar que las variables existen
RUN echo "Configuring GitHub authentication..." && \
    if [ -z "$GITHUB_TOKEN" ]; then \
        echo "ERROR: GITHUB_TOKEN is not set"; \
        exit 1; \
    fi && \
    if [ -z "$GITHUB_USERNAME" ]; then \
        echo "ERROR: GITHUB_USERNAME is not set"; \
        exit 1; \
    fi && \
    echo "GitHub username: $GITHUB_USERNAME"

# Crear settings.xml con credenciales de GitHub para acceder a packages privados
# Crear settings.xml con credenciales y repositorio de GitHub Packages
RUN mkdir -p /root/.m2 && \
    echo '<?xml version="1.0" encoding="UTF-8"?>' > /root/.m2/settings.xml && \
    echo '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"' >> /root/.m2/settings.xml && \
    echo '          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"' >> /root/.m2/settings.xml && \
    echo '          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">' >> /root/.m2/settings.xml && \
    echo '  <profiles>' >> /root/.m2/settings.xml && \
    echo '    <profile>' >> /root/.m2/settings.xml && \
    echo '      <id>github</id>' >> /root/.m2/settings.xml && \
    echo '      <repositories>' >> /root/.m2/settings.xml && \
    echo '        <repository>' >> /root/.m2/settings.xml && \
    echo '          <id>central</id>' >> /root/.m2/settings.xml && \
    echo '          <url>https://repo1.maven.org/maven2</url>' >> /root/.m2/settings.xml && \
    echo '        </repository>' >> /root/.m2/settings.xml && \
    echo '        <repository>' >> /root/.m2/settings.xml && \
    echo '          <id>github</id>' >> /root/.m2/settings.xml && \
    echo '          <url>https://maven.pkg.github.com/Elcolora3x/Commons-Mobyapp</url>' >> /root/.m2/settings.xml && \
    echo '          <snapshots><enabled>true</enabled></snapshots>' >> /root/.m2/settings.xml && \
    echo '        </repository>' >> /root/.m2/settings.xml && \
    echo '      </repositories>' >> /root/.m2/settings.xml && \
    echo '    </profile>' >> /root/.m2/settings.xml && \
    echo '  </profiles>' >> /root/.m2/settings.xml && \
    echo '  <activeProfiles>' >> /root/.m2/settings.xml && \
    echo '    <activeProfile>github</activeProfile>' >> /root/.m2/settings.xml && \
    echo '  </activeProfiles>' >> /root/.m2/settings.xml && \
    echo '  <servers>' >> /root/.m2/settings.xml && \
    echo '    <server>' >> /root/.m2/settings.xml && \
    echo '      <id>github</id>' >> /root/.m2/settings.xml && \
    echo "      <username>${GITHUB_USERNAME}</username>" >> /root/.m2/settings.xml && \
    echo "      <password>${GITHUB_TOKEN}</password>" >> /root/.m2/settings.xml && \
    echo '    </server>' >> /root/.m2/settings.xml && \
    echo '  </servers>' >> /root/.m2/settings.xml && \
    echo '</settings>' >> /root/.m2/settings.xml


# Verificar que settings.xml se creó
RUN echo "Settings.xml created successfully" && \
    cat /root/.m2/settings.xml | grep -v password

# Copiar código fuente
COPY src ./src
RUN mvn dependency:get -Dartifact=io.github.elcolora3x:libreriaclasescomunes:1.3 -X

# Compilar la aplicación (esto descarga dependencias de GitHub automáticamente)
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Crear usuario no-root
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Copiar el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Crear directorio para base de datos H2 (si se usa persistencia)
RUN mkdir -p /app/data && chown -R appuser:appgroup /app

USER appuser

# Exponer puerto
EXPOSE ${PROJECT_PORT:-8085}

# Variables de entorno
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:${PROJECT_PORT:-8085}/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]


