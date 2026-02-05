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
@Table(name="TB_PARAMETRO_GERAL")
public class ParametrizacaoGeral {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
	@Column(name = "DESIGNACAO",length = 200)
	private String designacao;
	
	@Column(name = "CHAVE",length = 200)
	private String chave;
	
	@Column(name = "VALOR",length = 4000)
	private String valor;
	
	@Column(name = "TIPO_DADO",length = 50)
	private String tipoDado;

}
