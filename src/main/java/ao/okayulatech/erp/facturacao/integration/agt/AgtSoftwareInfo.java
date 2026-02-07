package ao.okayulatech.erp.facturacao.integration.agt;

public class AgtSoftwareInfo {
	private AgtSoftwareInfoDetail softwareInfoDetail;
	private String jwsSoftwareSignature;

	public AgtSoftwareInfoDetail getSoftwareInfoDetail() {
		return softwareInfoDetail;
	}

	public void setSoftwareInfoDetail(AgtSoftwareInfoDetail softwareInfoDetail) {
		this.softwareInfoDetail = softwareInfoDetail;
	}

	public String getJwsSoftwareSignature() {
		return jwsSoftwareSignature;
	}

	public void setJwsSoftwareSignature(String jwsSoftwareSignature) {
		this.jwsSoftwareSignature = jwsSoftwareSignature;
	}
}
