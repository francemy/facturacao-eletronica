package ao.okayulatech.erp.facturacao.integration.agt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgtSubmissionPayload {
	private String submissionUUID;
	private String taxRegistrationNumber;
	private LocalDateTime submissionTimestamp;
	private AgtSoftwareInfo softwareInfo;
	private List<AgtDocument> documents = new ArrayList<>();

	public String getSubmissionUUID() {
		return submissionUUID;
	}

	public void setSubmissionUUID(String submissionUUID) {
		this.submissionUUID = submissionUUID;
	}

	public String getTaxRegistrationNumber() {
		return taxRegistrationNumber;
	}

	public void setTaxRegistrationNumber(String taxRegistrationNumber) {
		this.taxRegistrationNumber = taxRegistrationNumber;
	}

	public LocalDateTime getSubmissionTimestamp() {
		return submissionTimestamp;
	}

	public void setSubmissionTimestamp(LocalDateTime submissionTimestamp) {
		this.submissionTimestamp = submissionTimestamp;
	}

	public AgtSoftwareInfo getSoftwareInfo() {
		return softwareInfo;
	}

	public void setSoftwareInfo(AgtSoftwareInfo softwareInfo) {
		this.softwareInfo = softwareInfo;
	}

	public List<AgtDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<AgtDocument> documents) {
		this.documents = documents;
	}
}
