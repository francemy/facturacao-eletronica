package ao.okayulatech.erp.facturacao.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TB_EMPRESA_FACTURACAO")
public class EmpresaFacturacao {
	@Id
	@Column(name="NIF", length = 30)
	private String nif;
	@Column(name="NOME", length = 300)
	private String nome;
	@Column(name="EMAIL", length = 100)
	private String email;
	@Column(name="TELEFONE", length = 12)
	private String telefone;
	@Column(name="DATA_REGISTO")
	private LocalDateTime dataRegisto=LocalDateTime.now();
	@Column(name="DATA_INICIO_CONTRATO")
	private LocalDate dataInicioContrato;
	@Column(name="DATA_FIM_CONTRATO")
	private LocalDate dataFimContrato;
	
	public EmpresaFacturacao() {}
	public EmpresaFacturacao(String nif) {this.nif=nif;}
}
