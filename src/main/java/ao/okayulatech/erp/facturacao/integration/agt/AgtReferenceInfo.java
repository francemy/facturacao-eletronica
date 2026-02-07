package ao.okayulatech.erp.facturacao.integration.agt;

public class AgtReferenceInfo {
	private String reference;
	private String reason;
	private String referenceItemLineNo;

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReferenceItemLineNo() {
		return referenceItemLineNo;
	}

	public void setReferenceItemLineNo(String referenceItemLineNo) {
		this.referenceItemLineNo = referenceItemLineNo;
	}
}
