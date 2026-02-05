package ao.okayulatech.erp.facturacao.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TB_FACTURA_DETALHE")
public class FacturaDetalhe {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "LINE_NUMBER", nullable = false)
    private Integer lineNumber;
    @Column(name = "PRODUCT_CODE", length = 50, nullable = false)
    private String productCode;
    @Column(name = "PRODUCT_DESCRIPTION", length = 255)
    private String productDescription;
    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;
    @Column(name = "UNIT_OF_MEASURE", length = 10, nullable = false)
    private String unitOfMeasure;
    @Column(name = "UNIT_PRICE", precision = 18, scale = 2, nullable = false)
    private BigDecimal unitPrice;
    @Column(name = "UNIT_PRICE_BASE", precision = 18, scale = 2)
    private BigDecimal unitPriceBase;
    @Column(name = "DEBIT_AMOUNT", precision = 18, scale = 2)
    private BigDecimal debitAmount;
    @Column(name = "CREDIT_AMOUNT", precision = 18, scale = 2)
    private BigDecimal creditAmount;
    @Column(name = "SETTLEMENTE_AMOUNT", precision = 18, scale = 2)
    private BigDecimal settlementAmount;
    @Column(name = "REFERENCE", length = 50)
    private String reference;
    @Column(name = "REFERENCE_INTEM_LINE_NO", precision = 18, scale = 2)
    private BigDecimal referenceItemLineNo;
    
    @ManyToOne
	@JoinColumn(name="FACTURA_ID")
	private Factura factura;
}
