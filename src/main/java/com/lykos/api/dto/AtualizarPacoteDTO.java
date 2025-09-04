package com.lykos.domain.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class AtualizarPacoteDTO {

    @NotNull(message = "preco é obrigatório")
    @DecimalMin(value = "0.01", message = "preco deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "prazoDias é obrigatório")
    @Min(value = 1, message = "prazoDias deve ser maior que zero")
    private Integer prazoDias;

    @NotBlank(message = "descricao é obrigatória")
    private String descricao;

    public AtualizarPacoteDTO() {}

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getPrazoDias() { return prazoDias; }
    public void setPrazoDias(Integer prazoDias) { this.prazoDias = prazoDias; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}

//Temida atualização militar, fiz de um forma onde para atualizar exijo que os 3 campos venham, assim a API força o cliente a enviar valores “fixos” novamente.
