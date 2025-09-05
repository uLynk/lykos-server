package com.lykos.domain.servico;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lykos.domain.dto.CriarServicoDTO;
import com.lykos.domain.entidade.Pacote;
import com.lykos.domain.entidade.Servico;
import com.lykos.domain.repositorio.PacoteRepositorio;
import com.lykos.domain.repositorio.ServicoRepositorio;
import com.lykos.exception.RegraNegocioException;

@Service
public class ServicoServico {

    private final ServicoRepositorio servicoRepositorio;
    private final PacoteRepositorio pacoteRepositorio;

    public ServicoServico(ServicoRepositorio servicoRepositorio, PacoteRepositorio pacoteRepositorio) {
        this.servicoRepositorio = servicoRepositorio;
        this.pacoteRepositorio = pacoteRepositorio;
    }

    @Transactional
    public Servico criarServico(CriarServicoDTO dto) {
        // valida dto (freelancerId e pacoteId já são @NotNull no DTO)
        if (dto.getFreelancerId() == null) {
            throw new RegraNegocioException("freelancerId é obrigatório");
        }

        Pacote pacote = pacoteRepositorio.findById(dto.getPacoteId())
                .orElseThrow(() -> new RegraNegocioException("Pacote inválido. Escolha um pacote pré-definido."));

        // Reforçar que o pacote tem os campos obrigatórios (defensivo)
        if (pacote.getPreco() == null || pacote.getPreco().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Pacote inválido: preço inválido");
        }
        if (pacote.getPrazoDias() == null || pacote.getPrazoDias() <= 0) {
            throw new RegraNegocioException("Pacote inválido: prazo inválido");
        }
        if (pacote.getDescricao() == null || pacote.getDescricao().isBlank()) {
            throw new RegraNegocioException("Pacote inválido: descrição obrigatória");
        }

        Servico servico = new Servico(dto.getFreelancerId(), pacote);
        return servicoRepositorio.save(servico);
    }
    //Fiz essa validação pois, mesmo que Pacote tenha Builder, pode haver pacotes antigos no banco com dados inválidos.
}
