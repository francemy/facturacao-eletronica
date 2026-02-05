package ao.okayulatech.erp.facturacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ao.okayulatech.erp.facturacao.model.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Long>{

}
