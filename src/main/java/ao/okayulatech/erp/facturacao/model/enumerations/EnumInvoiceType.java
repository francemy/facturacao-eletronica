package ao.okayulatech.erp.facturacao.model.enumerations;

public enum EnumInvoiceType {
	FT("FT"),
	FR("FR"),
	GF("GF"),
	FG("FG"),
	AC("AC"),
	AR("AR"),
	TV("TV"),
	RG("RG"),
	RE("RE"),
	ND("ND"),
	NC("NC"),
	AF("AF"),
	RP("RP"),
	RA("RA"),
	CS("CS"),
	LD("LD");
	
	private EnumInvoiceType(String v) {
		this.value = v;
	}

	private String value;

	public String getValue() {
        return value;
    }
}
