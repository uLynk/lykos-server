package com.lykos.domain.entidade;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "taxacomissao")
public class TaxaComissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "percentual", precision = 5, scale = 2, nullable = false)
    private BigDecimal percentual;

    @Column(name = "descricao")
    private String descricao;

    protected TaxaComissao() {}
    public Long getId() { return id; }
    public BigDecimal getPercentual() { return percentual; }
    public String getDescricao() { return descricao; }
}
