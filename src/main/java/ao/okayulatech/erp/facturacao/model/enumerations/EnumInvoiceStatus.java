package ao.okayulatech.erp.facturacao.model.enumerations;

public enum EnumInvoiceStatus {
	N("N"),
	S("S"),
	A("A"),
	R("R"),
	F("F");
	
	private String value;

	private EnumInvoiceStatus(String v) {
		this.value = v;
	}

	public String getValue() {
        return value;
    }
}
