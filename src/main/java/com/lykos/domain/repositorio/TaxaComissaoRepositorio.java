package com.lykos.domain.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lykos.domain.entidade.TaxaComissao;
import java.util.Optional;

public interface TaxaComissaoRepositorio extends JpaRepository<TaxaComissao, Long> {
    Optional<TaxaComissao> findTopByOrderByIdAsc(); // pega a primeira linha como taxa default
}
