# File Tree: facturacao-eletronica

**Generated:** 2/7/2026, 7:18:10 PM
**Root Path:** `c:\Users\IT\Documents\Okayulatech\factura-eletronica\facturacao-eletronica`

```
├── .github
│   └── java-upgrade
│       ├── 20260206145527
│       │   └── progress.md
│       ├── 20260206145559
│       │   ├── plan.md
│       │   └── progress.md
│       └── .gitignore
├── examples
│   └── fatura-global.json
├── openapi
│   └── openapi.yaml
├── postman
│   └── facturacao-eletronica.postman_collection.json
├── scripts
│   └── sql
│       └── init.sql
├── src
│   ├── main
│   │   ├── java
│   │   │   └── ao
│   │   │       └── okayulatech
│   │   │           └── erp
│   │   │               ├── config
│   │   │               │   ├── JacksonConfig.java
│   │   │               │   ├── OpenApiConfig.java
│   │   │               │   └── SecurityConfig.java
│   │   │               ├── facturacao
│   │   │               │   ├── DTO
│   │   │               │   │   ├── DocumentSaleDTO.java
│   │   │               │   │   ├── DocumentTotalsDTO.java
│   │   │               │   │   ├── FacturaDTO.java
│   │   │               │   │   ├── LineSaleDTO.java
│   │   │               │   │   ├── SalesDocumentRequestDTO.java
│   │   │               │   │   ├── SoftwareInfoDTO.java
│   │   │               │   │   ├── SoftwareInfoDetailDTO.java
│   │   │               │   │   ├── TaxDTO.java
│   │   │               │   │   └── WithholdingTaxDTO.java
│   │   │               │   ├── api
│   │   │               │   │   └── dto
│   │   │               │   │       ├── CustomerDTO.java
│   │   │               │   │       ├── DocumentLineDTO.java
│   │   │               │   │       ├── DocumentRequest.java
│   │   │               │   │       ├── DocumentResponse.java
│   │   │               │   │       ├── DocumentTotalsDTO.java
│   │   │               │   │       ├── InvoiceRequest.java
│   │   │               │   │       ├── InvoiceResponse.java
│   │   │               │   │       ├── ReferenceInfoDTO.java
│   │   │               │   │       ├── SignRequest.java
│   │   │               │   │       ├── SoftwareInfoDTO.java
│   │   │               │   │       ├── SoftwareInfoDetailDTO.java
│   │   │               │   │       ├── TaxDTO.java
│   │   │               │   │       └── WithholdingTaxDTO.java
│   │   │               │   ├── controller
│   │   │               │   │   ├── ApiExceptionHandler.java
│   │   │               │   │   ├── ControllerEmissaoFactura.java
│   │   │               │   │   └── InvoiceController.java
│   │   │               │   ├── model
│   │   │               │   │   ├── enumerations
│   │   │               │   │   │   ├── EnumExemptionCode.java
│   │   │               │   │   │   ├── EnumInvoiceStatus.java
│   │   │               │   │   │   ├── EnumInvoiceType.java
│   │   │               │   │   │   ├── EnumTaxType.java
│   │   │               │   │   │   └── TaxCode.java
│   │   │               │   │   ├── Customer.java
│   │   │               │   │   ├── Document.java
│   │   │               │   │   ├── DocumentLine.java
│   │   │               │   │   ├── DocumentTotals.java
│   │   │               │   │   ├── EmpresaFacturacao.java
│   │   │               │   │   ├── EmpresaFacturacaoChaves.java
│   │   │               │   │   ├── EmpresaFacturacaoSeries.java
│   │   │               │   │   ├── FactoraDetalheImposto.java
│   │   │               │   │   ├── Factura.java
│   │   │               │   │   ├── FacturaDetalhe.java
│   │   │               │   │   ├── InvoiceHeader.java
│   │   │               │   │   ├── ParametrizacaoGeral.java
│   │   │               │   │   ├── PaymentReceipt.java
│   │   │               │   │   ├── ReferenceInfo.java
│   │   │               │   │   ├── SoftwareInfo.java
│   │   │               │   │   ├── SoftwareInfoDetail.java
│   │   │               │   │   ├── SourceDocument.java
│   │   │               │   │   ├── Tax.java
│   │   │               │   │   ├── TipoFactura.java
│   │   │               │   │   ├── TipoFacturaEstrutura.java
│   │   │               │   │   ├── TipoImposto.java
│   │   │               │   │   ├── TipoImpostoCode.java
│   │   │               │   │   └── WithholdingTax.java
│   │   │               │   ├── repository
│   │   │               │   │   ├── DocumentRepository.java
│   │   │               │   │   ├── EmpresaFacturacaoChavesRepository.java
│   │   │               │   │   ├── EmpresaFacturacaoRepository.java
│   │   │               │   │   ├── EmpresaFacturacaoSeriesRepository.java
│   │   │               │   │   ├── FactoraDetalheImpostoRepository.java
│   │   │               │   │   ├── FacturaDetalheRepository.java
│   │   │               │   │   ├── FacturaRepository.java
│   │   │               │   │   ├── InvoiceHeaderRepository.java
│   │   │               │   │   ├── ParametrizacaoGeralRepository.java
│   │   │               │   │   ├── TipoFacturaEstruturaRepository.java
│   │   │               │   │   ├── TipoFacturaRepository.java
│   │   │               │   │   ├── TipoImpostoCodeRepository.java
│   │   │               │   │   └── TipoImpostoRepository.java
│   │   │               │   └── service
│   │   │               │       ├── impl
│   │   │               │       │   ├── FacturaDetalheImpostoImpl.java
│   │   │               │       │   ├── FacturaDetalheServiceImpl.java
│   │   │               │       │   └── FacturaServiceImpl.java
│   │   │               │       ├── FacturaDetalheImposto.java
│   │   │               │       ├── FacturaDetalheService.java
│   │   │               │       ├── FacturaService.java
│   │   │               │       └── InvoiceService.java
│   │   │               ├── security
│   │   │               │   ├── dto
│   │   │               │   │   ├── AuthRequest.java
│   │   │               │   │   └── AuthResponse.java
│   │   │               │   ├── AuthController.java
│   │   │               │   ├── JwtAuthenticationFilter.java
│   │   │               │   ├── JwtService.java
│   │   │               │   └── UserConfig.java
│   │   │               ├── OkayulatechErpApplication.java
│   │   │               └── ServletInitializer.java
│   │   └── resources
│   │       ├── application-docker.properties
│   │       └── application.properties
│   └── test
│       ├── java
│       │   └── ao
│       │       └── okayulatech
│       │           └── erp
│       │               └── OkayulatechErpApplicationTests.java
│       └── resources
│           └── application-test.properties
├── .gitignore
├── Dockerfile
├── README.md
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── rewrite.yml
```

---
*Generated by FileTree Pro Extension*