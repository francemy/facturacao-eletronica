package ao.okayulatech.erp.facturacao.integration.agt;

public class AgtSoftwareInfoDetail {
	private String productId;
	private String productVersion;
	private String softwareValidationNumber;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	public String getSoftwareValidationNumber() {
		return softwareValidationNumber;
	}

	public void setSoftwareValidationNumber(String softwareValidationNumber) {
		this.softwareValidationNumber = softwareValidationNumber;
	}
}
