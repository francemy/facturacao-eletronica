# Módulo `facturacao.integration.agt`

Este módulo concentra **toda a integração outbound** com a AGT (Administração Geral Tributária), isolando a lógica de comunicação externa do restante domínio.

## Objetivo

- Centralizar configuração de endpoints e credenciais.
- Montar o payload final exigido pela AGT.
- Enviar submissões (POST) com token/autenticação.
- Traduzir erros de resposta para o domínio.

## Componentes principais

### 1) Configuração
- **`AgtProperties`**: carrega `agt.base-url`, `agt.auth-url`, `agt.client-id`, `agt.client-secret`, etc.
- **`AgtConfig`**: fornece um `RestClient` já com `baseUrl` configurada.

### 2) Autenticação
- **`AgtAuthClient`**: busca o token da AGT (endpoint `/token`).
  - Se a AGT exigir OAuth2/mTLS, este é o ponto de ajuste.

### 3) Cliente de submissão
- **`AgtClient`**: envia o payload para `/submissions`.
  - Adiciona `Authorization: Bearer <token>`.
  - Usa `AgtErrorTranslator` para erros HTTP.

### 4) Mapper
- **`AgtMapper`**: converte o modelo interno (`InvoiceHeader`, `Document`, etc.) para o payload final da AGT.

### 5) Payloads/DTOs
Modelos que representam a estrutura enviada à AGT:

- `AgtSubmissionPayload`
- `AgtDocument`, `AgtDocumentLine`, `AgtDocumentTotals`
- `AgtCustomer`, `AgtTax`, `AgtWithholdingTax`
- `AgtSoftwareInfo`, `AgtSoftwareInfoDetail`
- `AgtReferenceInfo`

### 6) Respostas e erros
- **`AgtSubmissionResponse`**: modelo da resposta da AGT.
- **`AgtErrorTranslator`**: converte erro HTTP em exceção do domínio.

## Fluxo resumido

1. `InvoiceService` cria o `InvoiceHeader`.
2. `SubmissionWorkflowService` gera evento outbox.
3. `OutboxProcessor` usa `AgtMapper` → `AgtSubmissionPayload`.
4. `AgtClient` autentica (`AgtAuthClient`) e envia para a AGT.

## Onde ajustar quando integrar com a AGT real

- **Formato final do payload:** ajuste `AgtSubmissionPayload` + `AgtMapper`.
- **Auth/token:** ajuste `AgtAuthClient`.
- **Endpoints:** ajuste `agt.base-url` e `agt.auth-url` em `application.properties`.

