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
@Table(name="TB_FACTURA_DETALHE_IMPOSTO")
public class FactoraDetalheImposto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TAX_COUNTRY_REGION", length = 5, nullable = false)
    private String taxCountryRegion;

    @Column(name = "TAX_PERCENTAGE", precision = 5, scale = 2, nullable = false)
    private BigDecimal taxPercentage;

    @Column(name = "TAX_CONTRIBUTION", precision = 18, scale = 2, nullable = false)
    private BigDecimal taxContribution;
    
    @ManyToOne
	@JoinColumn(name="FACTURA_DETALHE_ID")
	private FacturaDetalhe FacturaDetalhe;
    
    @ManyToOne
	@JoinColumn(name="TIPO_IMPOSTO_CODE_ID")
	private TipoImpostoCode tipoImpostoCode;
}
