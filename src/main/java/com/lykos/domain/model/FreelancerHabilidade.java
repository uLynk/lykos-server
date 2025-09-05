package com.lykos.domain.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "freelancer_habilidade")
public class FreelancerHabilidade {
    @EmbeddedId
    private FreelancerHabilidadeId id;
    
    @ManyToOne
    @MapsId("idFreelancer")
    @JoinColumn(name = "id_freelancer")
    private Freelancer freelancer;
    
    @ManyToOne
    @MapsId("idHabilidade")
    @JoinColumn(name = "id_habilidade")
    private Habilidade habilidade;
}

@Embeddable
class FreelancerHabilidadeId implements java.io.Serializable {
    private Long idFreelancer;
    public Long getIdFreelancer() {
        return idFreelancer;
    }
    private Long idHabilidade;
    public Long getIdHabilidade() {
        return idHabilidade;
    }
}