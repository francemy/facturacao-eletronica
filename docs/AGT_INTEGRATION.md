# Integração AGT + Assinatura + Outbox (Passo a passo)

Este documento explica como configurar e usar a integração com a AGT, assinatura JWS e o fluxo de submissão com outbox.

## 1) Configuração de propriedades (application.properties)

### AGT
Defina os endpoints e credenciais da AGT:

```properties
agt.base-url=https://api.agt.local
agt.auth-url=https://auth.agt.local
agt.client-id=change-me
agt.client-secret=change-me
agt.outbox-interval-ms=5000
```

### Assinatura
A assinatura usa um keystore (JKS/PKCS12). Preencha os dados do certificado:

```properties
signature.key-store-path=classpath:certs/keystore.p12
signature.key-store-password=changeme
signature.key-alias=agt-signing
signature.key-password=changeme
signature.algorithm=RS256
```

> Se o keystore não estiver configurado, o sistema gera uma chave efêmera (apenas para dev/test).

## 2) Preparar o certificado

1. Gere/obtenha o certificado emitido para o software de faturação.
2. Importe para um keystore (exemplo em PKCS12):

```bash
keytool -importkeystore \
  -srckeystore certificado.pfx -srcstoretype PKCS12 \
  -destkeystore keystore.p12 -deststoretype PKCS12
```

3. Coloque o ficheiro em `src/main/resources/certs/keystore.p12` (ou outro caminho).

## 3) Fluxo de submissão (como funciona)

### 3.1 Emissão interna
Ao chamar o endpoint de criação de faturas (`POST /api/invoices`):

1. O `InvoiceService` valida regras fiscais (totais e tipos).
2. Gera assinaturas JWS (documento + software).
3. Persiste a fatura e cria um `Submission`.
4. Cria um evento de `Outbox` com o payload da AGT.

### 3.2 Outbox Processor (envio para AGT)

O `OutboxProcessor` roda de forma agendada:

- Busca eventos pendentes.
- Envia para a AGT via `AgtClient`.
- Atualiza `Submission` com o estado.
- Guarda `AgtReceipt` e logs de request/response.

## 4) Onde ligar a equipa de dev

### A) Adicionar endpoint real da AGT
O payload já é criado pelo `AgtMapper`. A equipa só precisa:

- Confirmar o formato final exigido pela AGT.
- Ajustar `AgtSubmissionPayload` se houver campos obrigatórios adicionais.

### B) Substituir token/auth
O `AgtAuthClient` assume um endpoint `/token` com `clientId/clientSecret`.

Se a AGT usar OAuth2, mTLS ou outro fluxo:

- Ajustar o método `fetchToken()` para o handshake correto.

### C) Validar assinatura
O `SignatureService` assina um JSON canônico dos dados essenciais do documento e software.

A equipa pode:

- Ajustar o payload de assinatura conforme o manual da AGT.
- Validar a assinatura em ambiente de homologação.

### D) Ciclo fiscal e reprocessamento
Para reprocessar submissões falhadas:

- Atualizar `OutboxEvent.status` para `PENDING` e `nextAttemptAt` para `now()`.
- O scheduler vai tentar novamente.

## 5) Checklist de deploy

- [ ] AGT endpoints configurados
- [ ] Certificado instalado e keystore configurado
- [ ] Credenciais de AGT válidas
- [ ] Logging e correlation-id ativos
- [ ] Banco com novas tabelas (`submissions`, `outbox_events`, `agt_receipts`, etc.)

## 6) Estrutura criada

Principais módulos adicionados:

- `facturacao.integration.agt` → cliente, mapeador e payloads
- `facturacao.service.crypto` → assinatura JWS
- `facturacao.service.submission` → outbox + submissão
- `facturacao.model.submission` → entidades de submissão
- `logback-spring.xml` → logging com correlation-id
