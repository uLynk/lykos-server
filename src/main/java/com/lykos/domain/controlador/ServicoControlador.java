package com.lykos.domain.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.lykos.domain.dto.CriarServicoDTO;
import com.lykos.domain.entidade.Servico;
import com.lykos.domain.servico.ServicoServico;

@RestController
@RequestMapping("/api/servicos")
public class ServicoControlador {

    private final ServicoServico servicoServico;

    public ServicoControlador(ServicoServico servicoServico) {
        this.servicoServico = servicoServico;
    }

    @PostMapping
    public ResponseEntity<Servico> criar(@Valid @RequestBody CriarServicoDTO dto) {
        Servico servico = servicoServico.criarServico(dto);
        return ResponseEntity.ok(servico);
    }
}
