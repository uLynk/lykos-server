package com.lykos.domain.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lykos.domain.entidade.Pacote;

public interface PacoteRepositorio extends JpaRepository<Pacote, Long> { }