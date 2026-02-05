package ao.okayulatech.erp.facturacao.model.enumerations;

public enum EnumTaxType {
	IVA("IVA"),
	IS("IS"),
	NS("NS");

	private EnumTaxType(String v) {
		this.value = v;
	}
	
	private String value;

	public String getValue() {
        return value;
    }
}
