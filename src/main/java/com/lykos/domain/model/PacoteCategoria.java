package com.lykos.domain.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "pacote_categoria")
public class PacoteCategoria {
    @EmbeddedId
    private PacoteCategoriaId id;
    
    @ManyToOne
    @MapsId("idPacote")
    @JoinColumn(name = "id_pacote")
    private PacoteServico pacote;
    
    @ManyToOne
    @MapsId("idSubcategoria")
    @JoinColumn(name = "id_subcategoria")
    private Subcategoria subcategoria;
}

@Embeddable
class PacoteCategoriaId implements java.io.Serializable {
    private Long idPacote;
    public Long getIdPacote() {
        return idPacote;
    }
    private Long idSubcategoria;
    public Long getIdSubcategoria() {
        return idSubcategoria;
    }
}