package com.lykos.domain.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.lykos.domain.dto.CriarPacoteDTO;
import com.lykos.domain.dto.AtualizarPacoteDTO;
import com.lykos.domain.entidade.Pacote;
import com.lykos.domain.servico.PacoteServico;

@RestController
@RequestMapping("/api/pacotes")
public class PacoteControlador {

    private final PacoteServico pacoteServico;

    public PacoteControlador(PacoteServico pacoteServico) {
        this.pacoteServico = pacoteServico;
    }

    @PostMapping
    public ResponseEntity<Pacote> criar(@Valid @RequestBody CriarPacoteDTO dto) {
        Pacote criado = pacoteServico.criarPacote(dto);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pacote> atualizar(@PathVariable Long id, @Valid @RequestBody AtualizarPacoteDTO dto) {
        Pacote atualizado = pacoteServico.atualizarPacote(id, dto);
        return ResponseEntity.ok(atualizado);
    }
}
// Minha ideia é o seguinte, brothar. Requisição inválida / falta de campo → MethodArgumentNotValidException → manipulador global converte para 400 com mensagem, se ligou.
//RegraNegocioException → 400 com mensagem detalhada.
//Se concorrência detectada (optimistic lock) → tratamos abaixo.
//Fiz de acordo com a proposta.