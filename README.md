# Vehicles Service

API REST em Spring Boot para cadastro e consulta de veículos construída sob o Clean Archtecture.

## Sumário
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Como rodar localmente](#como-rodar-localmente)
- [Testes e cobertura](#testes-e-cobertura)
- [Empacotamento e contêiner](#empacotamento-e-contêiner)

## Arquitetura
- **Stack**: Java 21, Spring Boot 3.2, Spring Web, Spring Validation, Spring Data JPA, Flyway, Lombok.
- **Build**: Gradle, Spotless (Google Java Format) e JaCoCo.
- **Banco**: Configuração padrão para PostgreSQL.

## Pré-requisitos
- JDK 21
- Docker 24+ (para build de imagem)

## Como rodar localmente
1. Instale dependências e compile:
   ```bash
   ./gradlew clean build
   ```
2. Suba a aplicação:
   ```bash
   ./gradlew bootRun
   ```
3. A API ficará disponível em `http://localhost:8999`.

## Testes e cobertura
- Execute os testes com relatório de cobertura JaCoCo:
  ```bash
  ./gradlew test jacocoTestReport
  ```
- O relatório HTML é gerado em `build/reports/jacoco/test/html/index.html`.

## Empacotamento e contêiner
- Gere o JAR executável:
  ```bash
  ./gradlew bootJar
  ```
- Construa a imagem Docker multi-stage:
  ```bash
  docker build -t vehicles:local .
  ```
- Rode localmente:
  ```bash
  docker run --rm -p 8999:8999 vehicles:local
  ```
