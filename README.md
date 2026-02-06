# Facturação Eletrónica

Este repositório disponibiliza a base do serviço de facturação eletrónica (API) e documentação de exemplos de payload para emissão de facturas.

## Endpoints

Base URL (configurado em `application.properties`):

```
http://localhost:8833/api-okafaturacao
```

### Emissão de Factura (Factura Global)

**POST** `/fatura-geral/v1/registar`

Exemplo de payload (Factura Global):

- `examples/fatura-global.json`

Exemplo de chamada:

```bash
curl -X POST \
  http://localhost:8833/api-okafaturacao/fatura-geral/v1/registar \
  -H "Content-Type: application/json" \
  --data @examples/fatura-global.json
```

## Exemplos

| Nome | Ficheiro | Observações |
| --- | --- | --- |
| Factura Global | `examples/fatura-global.json` | Exemplo único para emissão de factura global. |

> **Nota:** Se forem necessários múltiplos exemplos, use sufixos numéricos e documente todos no quadro acima (ex.: `fatura-global-1.json`, `fatura-global-2.json`).
