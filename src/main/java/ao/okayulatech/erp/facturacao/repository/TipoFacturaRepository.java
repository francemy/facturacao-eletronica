package ao.okayulatech.erp.facturacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ao.okayulatech.erp.facturacao.model.TipoFactura;

public interface TipoFacturaRepository extends JpaRepository<TipoFactura, Long>{

	@Query(value = "SELECT * FROM TB_TIPO_FACTURA A WHERE A.ABREVIACAO=?1",nativeQuery = true)
	TipoFactura BUSCAR_TIPO_DE_FACTURAS(String abreviacao);
}
