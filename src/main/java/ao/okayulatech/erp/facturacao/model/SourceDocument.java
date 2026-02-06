package ao.okayulatech.erp.facturacao.model;

import java.time.LocalDate;
import java.util.UUID;

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
@Table(name = "source_documents")
public class SourceDocument {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String originatingOn;
	private String invoiceNo;
	private LocalDate invoiceDate;
}
