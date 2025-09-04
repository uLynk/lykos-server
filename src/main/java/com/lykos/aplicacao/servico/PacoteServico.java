package com.lykos.aplicacao.servico;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lykos.api.dto.CriarPacoteDTO;
import com.lykos.api.dto.AtualizarPacoteDTO;
import com.lykos.dominio.entidade.Pacote;
import com.lykos.dominio.repositorio.PacoteRepositorio;
importcom.lykos.dominio.exception.RegraNegocioException;

import java.math.BigDecimal;

@Service
public class PacoteServico {

    private final PacoteRepositorio pacoteRepositorio;

    public PacoteServico(PacoteRepositorio pacoteRepositorio) {
        this.pacoteRepositorio = pacoteRepositorio;
    }

    @Transactional
    public Pacote criarPacote(CriarPacoteDTO dto) {
        // defensivo - validações extras
        validarCamposBasicos(dto.getNome(), dto.getDescricao(), dto.getPreco(), dto.getPrazoDias());

        Pacote p = new Pacote.Builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .prazoDias(dto.getPrazoDias())
                .build();

        return pacoteRepositorio.save(p);
    }

    @Transactional
    public Pacote atualizarPacote(Long id, AtualizarPacoteDTO dto) {
        Pacote existente = pacoteRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Pacote não encontrado"));

        // valida novo valores
        validarCamposBasicos(existente.getNome(), dto.getDescricao(), dto.getPreco(), dto.getPrazoDias());

        // aplicar atualizações (nome fica imutável neste fluxo; apenas descricao/preco/prazo)
        existente.setDescricao(dto.getDescricao());
        existente.setPreco(dto.getPreco());
        existente.setPrazoDias(dto.getPrazoDias());

        return pacoteRepositorio.save(existente);
    }

    private void validarCamposBasicos(String nome, String descricao, BigDecimal preco, Integer prazoDias) {
        if (nome == null || nome.isBlank()) {
            throw new RegraNegocioException("Nome do pacote inválido");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new RegraNegocioException("Descrição é obrigatória");
        }
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Preço deve ser maior que zero");
        }
        if (prazoDias == null || prazoDias <= 0) {
            throw new RegraNegocioException("Prazo (dias) deve ser maior que zero");
        }
    }
}
//expliquei um pouco pra ficar bem detalhado no que estou pensando
//Optei por deixar nome imutável no atualizarPacote, isso evita identificar pacotes com nomes trocados acidentalmente.