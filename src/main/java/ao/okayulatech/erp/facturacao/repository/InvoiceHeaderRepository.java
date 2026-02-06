package ao.okayulatech.erp.facturacao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ao.okayulatech.erp.facturacao.model.InvoiceHeader;

public interface InvoiceHeaderRepository extends JpaRepository<InvoiceHeader, UUID> {}
