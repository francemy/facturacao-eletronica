package ao.okayulatech.erp.facturacao.DTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineSaleDTO {
    private Long lineNumber;
    private String productCode;
    private String productDescription;
    private BigDecimal quantity;
    private String unitOfMeasure;
    private BigDecimal unitPrice;
    private BigDecimal unitPriceBase;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private BigDecimal settlementAmount;
    private List<TaxDTO> taxes=new ArrayList<TaxDTO>();
}
