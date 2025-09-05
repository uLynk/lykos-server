package com.lykos.domain.entidade;

import jakarta.persistence.*;

@Entity
@Table(name = "servico")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long freelancerId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pacote_id", nullable = false)
    private Pacote pacote;

    protected Servico() {}

    public Servico(Long freelancerId, Pacote pacote) {
        this.freelancerId = freelancerId;
        this.pacote = pacote;
    }

    public Long getId() { return id; }
    public Long getFreelancerId() { return freelancerId; }
    public Pacote getPacote() { return pacote; }

    //@ManyToOne(optional = false) força a presença de pacote no banco (não permite nulo).
}