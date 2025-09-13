package com.lykos.domain.dto;

import jakarta.validation.constraints.NotNull;

public class CriarContratacaoDTO {
    @NotNull
    private Long pacoteId;
    @NotNull
    private Long clienteId;
    // getters/setters
    public Long getPacoteId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPacoteId'");
    }
    public Long getClienteId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClienteId'");
    }
}
