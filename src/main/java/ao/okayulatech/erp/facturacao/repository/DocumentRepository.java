package ao.okayulatech.erp.facturacao.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ao.okayulatech.erp.facturacao.model.Document;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
	List<Document> findByDocumentNoContainingIgnoreCase(String documentNo);

	List<Document> findByDocumentType(String documentType);

	List<Document> findByDocumentDateBetween(LocalDate start, LocalDate end);
}
