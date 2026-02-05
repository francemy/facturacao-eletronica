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
@Table(name="TB_EMPRESA_FACTURACAO_CHAVES")
public class EmpresaFacturacaoChaves {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name = "PRIVATE_KEY",length = 4000)
	private String privateKey;
	@Column(name = "PUBLIC_KEY",length = 4000)
	private String publicKey;
	@Column(name="IS_ACTIVO")
	private boolean isActivo;
	@ManyToOne
	@JoinColumn(name="EMPRESA_FACTURACAO_ID")
	private EmpresaFacturacao empresaFacturacao;
}
