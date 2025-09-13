package com.lykos.domain.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.lykos.domain.dto.CriarContratacaoDTO;
import com.lykos.domain.dto.LiberarPagamentoDTO;
import com.lykos.domain.entidade.Contratacao;
import com.lykos.domain.servico.ContratacaoServico;

@RestController
@RequestMapping("/api/contratacoes")
public class ContratacaoControlador {

    private final ContratacaoServico contratacaoServico;

    public ContratacaoControlador(ContratacaoServico contratacaoServico) {
        this.contratacaoServico = contratacaoServico;
    }

    @PostMapping
    public ResponseEntity<Contratacao> criar(@Valid @RequestBody CriarContratacaoDTO dto) {
        Contratacao criado = contratacaoServico.criarContratacao(dto.getPacoteId(), dto.getClienteId());
        return ResponseEntity.ok(criado);
    }

    @PostMapping("/{id}/pagamento/registrar")
    public ResponseEntity<Contratacao> registrarPagamento(@PathVariable Long id) {
        Contratacao atualizado = contratacaoServico.registrarPagamento(id);
        return ResponseEntity.ok(atualizado);
    }

    @PostMapping("/{id}/pagamento/liberar")
    public ResponseEntity<String> liberarPagamento(@PathVariable Long id,
                                                   @Valid @RequestBody LiberarPagamentoDTO dto) {
        contratacaoServico.liberarPagamento(id, dto.getContaFreelancer());
        return ResponseEntity.ok("Pagamento liberado e transferido ao freelancer");
    }

    @PostMapping("/{id}/disputa/abrir")
    public ResponseEntity<String> abrirDisputa(@PathVariable Long id) {
        contratacaoServico.abrirDisputa(id);
        return ResponseEntity.ok("Disputa registrada");
    }
}
