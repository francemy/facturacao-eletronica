package ao.okayulatech.erp.facturacao.model;

import ao.okayulatech.erp.facturacao.model.enumerations.TaxCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="TB_TIPO_IMPOSTO_CODE")
public class TipoImpostoCode {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TAX_CODE")
	private TaxCode taxCode;
	
	@Column(name = "DESIGNACAO",length = 60)
	private String designacao;
	
	@ManyToOne
	@JoinColumn(name="TIPO_IMPOSTO_ID")
	private TipoImposto tipoImposto;

}
