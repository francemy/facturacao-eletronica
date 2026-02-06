# Facturação Eletrónica

Este repositório disponibiliza a base do serviço de facturação eletrónica (API) e documentação de exemplos de payload para emissão de facturas.

## Endpoints

Base URL (configurado em `application.properties`):

```
http://localhost:8833/api-okafaturacao
```

## Swagger / OpenAPI

Interface Swagger UI:

```
http://localhost:8833/api-okafaturacao/swagger-ui/index.html
```

OpenAPI JSON:

```
http://localhost:8833/api-okafaturacao/v3/api-docs
```

Documentação OpenAPI estática:

- `openapi/openapi.yaml`

## Autenticação

A API usa autenticação **JWT**. Para obter o token, use o endpoint de login:

**POST** `/api/auth/login` (base `/api-okafaturacao`)

```json
{
  "username": "oka-admin",
  "password": "oka-erp-2024"
}
```

As credenciais padrão podem ser alteradas em `application.properties`. O token deve ser enviado em `Authorization: Bearer <token>`.

> **Nota:** Se `jwsDocumentSignature` não for enviado, a API gera uma assinatura mock automaticamente para facilitar testes locais.

### Coleção Postman

- `postman/facturacao-eletronica.postman_collection.json`

### Docker

Suba o banco PostgreSQL via docker-compose:

```bash
docker-compose up -d
```

Depois inicie a aplicação com o perfil `docker`:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=docker
```

Scripts SQL disponíveis em `scripts/sql/init.sql`.

### Emissão de Factura (Factura Global)

**POST** `/fatura-geral/v1/registar`

Exemplo de payload (Factura Global):

- `examples/fatura-global.json`

Exemplo de chamada:

```bash
curl -X POST \
  http://localhost:8833/api-okafaturacao/fatura-geral/v1/registar \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  --data @examples/fatura-global.json
```

## Endpoints principais (gestão de documentos)

```
POST   /api/invoices
GET    /api/invoices
GET    /api/invoices/{id}
GET    /api/invoices/search?documentNo=&documentType=&dateFrom=&dateTo=
PUT    /api/invoices/{id}
DELETE /api/invoices/{id}
POST   /api/invoices/{id}/sign
GET    /api/invoices/{id}/export/saft
GET    /api/invoices/{id}/pdf
POST   /api/invoices/credit-note
POST   /api/invoices/debit-note
POST   /api/invoices/receipt
POST   /api/invoices/reversal
POST   /api/invoices/advance
```

## Exemplos

| Nome | Ficheiro | Observações |
| --- | --- | --- |
| Factura Global | `examples/fatura-global.json` | Exemplo único para emissão de factura global. |

> **Nota:** Se forem necessários múltiplos exemplos, use sufixos numéricos e documente todos no quadro acima (ex.: `fatura-global-1.json`, `fatura-global-2.json`).
