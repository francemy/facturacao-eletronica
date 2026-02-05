package ao.okayulatech.erp.facturacao.model;

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
@Table(name="TB_EMPRESA_FACTURACAO_SERIES")
public class EmpresaFacturacaoSeries {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name = "ESTABLISHMENT_NUMBER", length = 80)
	private String establishmentNumber;
	@Column(name = "SERIES_YEAR")
	private Integer seriesYear;
	@Column(name = "SERIES_CONTINGENCY_INDICATOR", length = 10)
	private String seriesContingencyIndicator;// S| N ONDE S É SERIE DO MODO DE CONTIGENCIA
	@Column(name = "IS_ACTIVO")
	private boolean isActivo;
	@ManyToOne
	@JoinColumn(name = "TIPO_FACTURA_ID")
	private TipoFactura documentType;
	@ManyToOne
	@JoinColumn(name = "EMPRESA_FACTURACAO_ID")
	private EmpresaFacturacao empresaFacturacao;

}
