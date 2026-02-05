package ao.okayulatech.erp.facturacao.model.enumerations;

public enum EnumExemptionCode {
	NOR("NOR"),
	ISE("ISE"),
	OUT("OUT");

	private EnumExemptionCode(String v) {
		this.value = v;
	}

	private String value;

	public String getValue() {
        return value;
    }
}
