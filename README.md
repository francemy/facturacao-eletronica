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

## Autenticação

A API usa autenticação **Basic**. Credenciais padrão (ver `application.properties`):

- Utilizador: `oka-admin`
- Palavra-passe: `oka-erp-2024`

### Emissão de Factura (Factura Global)

**POST** `/fatura-geral/v1/registar`

Exemplo de payload (Factura Global):

- `examples/fatura-global.json`

Exemplo de chamada:

```bash
curl -X POST \
  http://localhost:8833/api-okafaturacao/fatura-geral/v1/registar \
  -u oka-admin:oka-erp-2024 \
  -H "Content-Type: application/json" \
  --data @examples/fatura-global.json
```

## Exemplos

| Nome | Ficheiro | Observações |
| --- | --- | --- |
| Factura Global | `examples/fatura-global.json` | Exemplo único para emissão de factura global. |

> **Nota:** Se forem necessários múltiplos exemplos, use sufixos numéricos e documente todos no quadro acima (ex.: `fatura-global-1.json`, `fatura-global-2.json`).
