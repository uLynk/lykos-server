package com.lykos.domain.servico;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lykos.domain.entidade.Contratacao;
import com.lykos.domain.entidade.Pacote;
import com.lykos.domain.entidade.TaxaComissao;
import com.lykos.domain.repositorio.ContratacaoRepositorio;
import com.lykos.domain.repositorio.PacoteRepositorio;
import com.lykos.domain.repositorio.TaxaComissaoRepositorio;
import com.lykos.exception.RegraNegocioException;
import com.lykos.integracao.PagamentoGateway;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ContratacaoServico {

    private final ContratacaoRepositorio contratacaoRepositorio;
    private final PacoteRepositorio pacoteRepositorio;
    private final TaxaComissaoRepositorio taxaComissaoRepositorio;
    private final PagamentoGateway pagamentoGateway;

    public ContratacaoServico(ContratacaoRepositorio contratacaoRepositorio,
                              PacoteRepositorio pacoteRepositorio,
                              TaxaComissaoRepositorio taxaComissaoRepositorio,
                              PagamentoGateway pagamentoGateway) {
        this.contratacaoRepositorio = contratacaoRepositorio;
        this.pacoteRepositorio = pacoteRepositorio;
        this.taxaComissaoRepositorio = taxaComissaoRepositorio;
        this.pagamentoGateway = pagamentoGateway;
    }

    @Transactional
    public Contratacao criarContratacao(Long pacoteId, Long clienteId) {
        Pacote pacote = pacoteRepositorio.findById(pacoteId)
                .orElseThrow(() -> new RegraNegocioException("Pacote não encontrado"));

        BigDecimal preco = pacote.getPreco();
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Pacote com preço inválido");
        }

        Contratacao c = new Contratacao(pacoteId, clienteId, preco);
        // snapshot do pacote
        c.setPacoteNome(pacote.getTitulo());
        c.setPacotePreco(preco);
        c.setPacoteDescricao(pacote.getDescricao());
        c.setStatus(Contratacao.StatusPagamento.PENDENTE);

        return contratacaoRepositorio.save(c);
    }

    @Transactional
    public Contratacao registrarPagamento(Long contratacaoId) {
        Contratacao c = contratacaoRepositorio.findById(contratacaoId)
                .orElseThrow(() -> new RegraNegocioException("Contratação não encontrada"));

        if (c.isPagamentoProcessado()) {
            return c; // idempotência
        }

        BigDecimal percentual = obterPercentualComissao(); // 0.10 = 10%
        BigDecimal valorTotal = c.getValorTotal();
        if (valorTotal == null) {
            throw new RegraNegocioException("Valor total não definido para esta contratação");
        }

        BigDecimal comissao = valorTotal.multiply(percentual).setScale(2, RoundingMode.HALF_UP);
        BigDecimal valorLiquido = valorTotal.subtract(comissao).setScale(2, RoundingMode.HALF_UP);

        c.setComissao(comissao);
        c.setValorLiquido(valorLiquido);
        c.setPagamentoProcessado(true);
        c.setStatus(Contratacao.StatusPagamento.PAGO);
        // opcional: marcar AGUARDANDO_CONFIRMACAO em campo separado se quiser

        return contratacaoRepositorio.save(c);
    }

    @Transactional
    public boolean liberarPagamento(Long contratacaoId, String contaFreelancer) {
        Contratacao c = contratacaoRepositorio.findById(contratacaoId)
                .orElseThrow(() -> new RegraNegocioException("Contratação não encontrada"));

        if (!c.isPagamentoProcessado() || c.getStatus() != Contratacao.StatusPagamento.PAGO) {
            throw new RegraNegocioException("Pagamento ainda não registrado/confirmado");
        }

        if (c.isDisputaAberta()) {
            // manter estado; não liberar
            throw new RegraNegocioException("Liberação suspensa: disputa aberta para esta contratação");
        }

        if (c.isLiberado()) {
            return true; // idempotência
        }

        BigDecimal valor = c.getValorLiquido();
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Valor líquido inválido");
        }

        boolean sucesso = pagamentoGateway.transferir(contaFreelancer, valor);

        if (sucesso) {
            c.setLiberado(true);
            contratacaoRepositorio.save(c);
            return true;
        } else {
            throw new RegraNegocioException("Falha ao transferir para o freelancer");
        }
    }

    @Transactional(readOnly = true)
    private BigDecimal obterPercentualComissao() {
        // tenta ler do banco; se não existir, usa 10%
        return taxaComissaoRepositorio.findTopByOrderByIdAsc()
                .map(TaxaComissao::getPercentual)
                .orElse(BigDecimal.valueOf(0.10));
    }

    @Transactional
    public void abrirDisputa(Long contratacaoId) {
        Contratacao c = contratacaoRepositorio.findById(contratacaoId)
                .orElseThrow(() -> new RegraNegocioException("Contratação não encontrada"));
        c.setDisputaAberta(true);
        contratacaoRepositorio.save(c);
    }

    @Transactional
    public void encerrarDisputaELiberar(Long contratacaoId, String contaFreelancer) {
        Contratacao c = registrarPagamentoIfNeeded(contratacaoId);
        c.setDisputaAberta(false);
        contratacaoRepositorio.save(c);
        liberarPagamento(contratacaoId, contaFreelancer);
    }

    private Contratacao registrarPagamentoIfNeeded(Long contratacaoId) {
        Contratacao c = contratacaoRepositorio.findById(contratacaoId)
                .orElseThrow(() -> new RegraNegocioException("Contratação não encontrada"));
        if (!c.isPagamentoProcessado()) {
            throw new RegraNegocioException("Pagamento não registrado ainda");
        }
        return c;
    }
}
