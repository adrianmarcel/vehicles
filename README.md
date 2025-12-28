# Plataforma de revenda de veículos (Java)

Esta solução implementa os dois microserviços solicitados em Java com Spring Boot:

- **Main Service** (`main-service`): CRUD simplificado de veículos, reserva para pagamento e webhook de atualização de pagamento.
- **Sales Service** (`sales-service`): listagem de veículos disponíveis, criação de vendas e sincronização do status de pagamento.

Cada serviço mantém seu próprio banco de dados H2 e se comunica via HTTP usando `RestTemplate`.

## Pré-requisitos
- Java 17+
- Maven 3.9+

## Executando localmente
Em terminais separados:
```bash
mvn -pl main-service spring-boot:run
mvn -pl sales-service spring-boot:run
```

URLs padrão:
- Main Service: `http://localhost:8080`
- Sales Service: `http://localhost:8081`

As URLs dos serviços e bases podem ser alteradas via `application.properties` de cada módulo.

## Testes
Para rodar todos os testes automatizados:
```bash
mvn test
```
