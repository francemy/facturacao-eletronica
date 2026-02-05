package ao.okayulatech.erp.facturacao.model.enumerations;

public enum TaxCode {
	NOR("Taxa normal"), INT("Taxa intermédia"), RED("Taxa reduzida"), ISE("Isento"), OUT("Outra"),NS("NS");

	String descricao;

	TaxCode(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}