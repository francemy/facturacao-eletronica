# Visão Geral da API de Faturação Eletrónica (AGT)

## Introdução
A API de Faturação Eletrónica define o conjunto de interfaces, regras e padrões técnicos necessários para a comunicação automática de documentos fiscais entre os softwares de faturação certificados e a Administração Tributária. O objetivo é garantir a integridade, autenticidade e rastreabilidade dos documentos através de mecanismos como assinatura digital JWS e controle de séries.

A arquitetura é suportada por um modelo de processamento assíncrono, permitindo validações baseadas em filas de processamento, com consulta posterior (polling) ou envio de notificações callback (Disponível nas próximas versões). Este documento descreve a visão geral da API, o fluxo de integração ponta a ponta, o modelo de comunicação e os principais conceitos técnicos necessários para implementação correta da solução.

## Propósito da API
A API de Faturação Eletrónica tem como objetivo permitir que softwares de faturação comuniquem em tempo real (ou quase real) todos os documentos fiscais à Administração Tributária, garantindo:

- Integridade e autenticidade por meio de assinatura digital JWS.
- Validação automática e retorno do estado do documento (válido ou inválido).
- Sincronização entre os sistemas internos do contribuinte e a base fiscal.
- Auditoria e rastreabilidade completa (com requestID, timestamps e histórico de processamento).

## Arquitectura Geral da API
A arquitetura da solução é baseada no modelo de comunicação assíncrona.

### Processamento Assíncrono

1. O contribuinte envia o documento JSON.
2. A API devolve requestID imediatamente, confirmando apenas receção.
3. O documento entra na fila de processamento.
4. O contribuinte deve consultar (polling) ou aguardar callback.

Este modelo suporta:

- volumes grandes
- redução de latência no front-end
- isolamento de falhas

Mecanismos disponíveis:

- **Polling**: Cliente consulta a decisão final.
- **Callback (se ativado)**: A API faz POST para o endpoint do contribuinte quando o documento for validado (Disponível nas próximas versões).

## Autenticação & Autorização
A API utiliza o mecanismo Basic Authentication para validar e autorizar o acesso dos produtores de software.
O acesso é concedido através de um par de credenciais (username e password) emitido pela entidade gestora da Faturação Eletrónica.

Estas credenciais devem ser enviadas em todas as chamadas aos serviços protegidos da API.

### Obtenção das Credenciais
Para solicitar as credenciais de acesso, o produtor de software deve enviar um e-mail formal para:

📧 produtores.dfe.dcrr.agt@minfin.gov.ao

O pedido deve conter:

- Nome da Empresa
- NIF da Empresa

### Funcionamento do Basic Auth
O Basic Auth consiste em enviar as credenciais codificadas em Base64 no cabeçalho HTTP:

```
Authorization: Basic <Base64(username:password)>
```

Exemplo de codificação:

Se as credenciais forem:

- Username: cliente123
- Password: s3nh@F0rte!

Concatenação antes do Base64: `cliente123:s3nh@F0rte!`

Após Base64: `Y2xpZW50ZTEyMzpzM25oQEYwcnRlIQ==`

#### Exemplo de Header HTTP Completo

Requisição POST

```
POST /sigt/fe/v1/registarFactura HTTP/1.1
Host: https://sifphml.minfin.gov.ao
Authorization: Basic Y2xpZW50ZTEyMzpzM25oQEYwcnRlIQ==
Content-Type: application/json
Accept: application/json
Content-Length: 524
```

#### Exemplo em cURL

```
curl -X POST "https://sifphml.minfin.gov.ao/sigt/fe/v1/registarFactura" \
 -u cliente123:"s3nh@F0rte!" \
 -H "Accept: application/json"
```

> Nota: o curl já faz o Base64 automaticamente quando usamos `-u`.

#### Exemplo em JavaScript (Fetch API)

```javascript
const auth = btoa("cliente123:s3nh@F0rte!");

fetch("https://sifphml.minfin.gov.ao/sigt/fe/v1/registarFactura", {
  method: "POST",
  headers: {
    "Authorization": `Basic ${auth}`,
    "Content-Type": "application/json",
    "Accept": "application/json"
  }
}).then(res => res.json()).then(console.log);
```

#### Exemplo em C# (HttpClient)

```csharp
var client = new HttpClient();

var byteArray = Encoding.ASCII.GetBytes("cliente123:s3nh@F0rte!");

client.DefaultRequestHeaders.Authorization =
  new AuthenticationHeaderValue("Basic", Convert.ToBase64String(byteArray));

var response = await client.GetAsync("https://sifphml.minfin.gov.ao/sigt/fe/v1/registarFactura");
var content = await response.Content.ReadAsStringAsync();
```

## Estrutura das Assinaturas Digitais (JWS)
A API de Faturação Eletrónica utiliza JSON Web Signature (JWS) como mecanismo padrão para assinatura digital de:

- Informações do software (`jwsSoftwareSignature`)
- Documentos fiscais (`jwsDocumentSignature`)
- Requisições feitas ao serviço (`jwsSignature`)

O objetivo é garantir:

- Integridade – os dados não foram alterados.
- Autenticidade – o emissor é quem diz ser.
- Não-repúdio – o emissor não pode negar a autoria.

Cada assinatura utiliza a chave privada do contribuinte ou do produtor, dependendo do tipo de assinatura.

### Algoritmo Utilizado (RS256)
Todas as assinaturas JWS utilizam o algoritmo:

- RS256 – RSA com SHA-256

Características:

- Chave privada RSA a partir de 2048 bits (recomenda-se 4096).
- Hash criptográfico SHA-256.

Cabeçalho JWS:

```json
{
  "alg": "RS256",
  "typ": "JWT"
}
```

Para garantir melhor consistência, recomenda-se que o objeto a assinar seja transformado em JSON canônico antes da assinatura.

Regras de JSON canônico:

- Sem quebras de linha.
- Sem espaços ou indentação.
- Aspas duplas sempre obrigatórias.
- Números sem formatação adicional.

Exemplo de objeto:

```json
{
  "productId": "Nome",
  "productVersion": "1.0",
  "softwareValidationNumber": "123"
}
```

### Geração da Assinatura (Resumo do Processo)

1. Criar um objeto JSON (canônico recomendado) contendo apenas os campos exigidos.
2. Montar o JWS Compact Serialization:

```
base64url(header) + "." + base64url(payload)
```

3. Assinar com RSA-SHA256 usando a chave privada.
4. Gerar o JWS final:

```
header.payload.signature
```

### Assinatura `jwsSoftwareSignature`
Assina o objeto:

```json
{
  "productId": "",
  "productVersion": "",
  "softwareValidationNumber": ""
}
```

### Assinatura `jwsDocumentSignature`
Assina os elementos principais do documento fiscal, como:

```json
{
  "documentNo": "...",
  "taxRegistrationNumber": "...",
  "documentType": "...",
  "documentDate": "...",
  "customerTaxID": "...",
  "customerCountry": "...",
  "companyName": "...",
  "documentTotals": {
    "taxPayable": 70,
    "netTotal": 500,
    "grossTotal": 570
  }
}
```

### Assinatura `jwsSignature` (Assinatura da Requisição)
Assina o objeto principal da requisição — por exemplo:

```json
{
  "taxRegistrationNumber": "5001234567",
  "requestID": "REQ-000001"
}
```

## Erros Comuns na Geração de JWS

- Concatenar campos ao invés de assinar o JSON.
- Usar RSA com chave inferior a 2048 bits.
- Assinar com chave pública.
- Codificação Base64 normal (usar Base64URL, sem padding).

## Solicitar Criação de Série
Serviço destinado a solicitar a criação de séries de numeração de Faturas Electrónicas, devolvendo em resposta um indicador de sucesso ou insucesso.

### Endereços

- Homologação: `https://sifphml.minfin.gov.ao/sigt/fe/ws/v1/registarFactura`
- Produção: `https://sifp.minfin.gov.ao/sigt/fe/v1/solicitarSerie`

### Payload de entrada

```json
{
  "schemaVersion": "1.2",
  "submissionUUID": "a1b2c3d4-e5f6-7890-g1h2-i238j234k5122",
  "taxRegistrationNumber": "5001636863",
  "submissionTimeStamp": "2025-09-02T14:30:00Z",
  "softwareInfo": {
    "softwareInfoDetail": {
      "productId": "Meu ERP CERTO",
      "productVersion": "1.0.1",
      "softwareValidationNumber": "C_134"
    },
    "jwsSoftwareSignature": "<assinatura>"
  },
  "seriesYear": "2025",
  "documentType": "LD",
  "establishmentNumber": "10",
  "jwsSignature": "string",
  "seriesContingencyIndicator": "N"
}
```

### Payload assinatura Solicitar Série

```json
{
  "taxRegistrationNumber": "...",
  "seriesYear": "...",
  "documentType": "...",
  "establishmentNumber": "...",
  "seriesContingencyIndicator": "..."
}
```

### Payload de saída (resposta)

```json
{
  "resultCode": 1,
  "errorList": [""],
  "seriesFEResult": {
    "seriesCode": "LD6325S2042N",
    "authorizedQuantity": "999999999999",
    "firstDocumentNo": "1",
    "lastDocumentNo": "999999999999"
  }
}
```

### Códigos e mensagens de erro (exemplos)

- FE-RNG-010 → E08 (assinatura do produtor de software inválida)
- FE-RNG-011 → E39 (dados do software não coincidem com certificação)
- FE-RNG-032 → E40 (assinatura da chamada inválida)
- FE-RNG-050 → E06 (contribuinte não aderiu à facturação electrónica)
- FE-RNG-080 → E48 (estabelecimento desconhecido)

---

Este documento é um resumo técnico para referência da equipa de integração. Ajustes finos devem ser feitos conforme a versão oficial do manual da AGT.
