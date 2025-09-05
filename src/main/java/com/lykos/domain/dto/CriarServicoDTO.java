package com.lykos.domain.dto;

import jakarta.validation.constraints.NotNull;

public class CriarServicoDTO {

    @NotNull(message = "freelancerId é obrigatório")
    private Long freelancerId;

    @NotNull(message = "pacoteId é obrigatório")
    private Long pacoteId;

    public CriarServicoDTO() { }

    public Long getFreelancerId() { return freelancerId; }
    public void setFreelancerId(Long freelancerId) { this.freelancerId = freelancerId; }

    public Long getPacoteId() { return pacoteId; }
    public void setPacoteId(Long pacoteId) { this.pacoteId = pacoteId; }
}