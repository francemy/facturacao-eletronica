package ao.okayulatech.erp.facturacao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TB_TIPO_IMPOSTO")
public class TipoImposto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name = "DESIGNACAO",length = 100)
	private String designacao;
	@Column(name = "ABREVIACAO",length = 10)
	private String abrevicao;
	@Column(name = "IS_ACTIVO",length = 100)
	private boolean isActivo;
}
