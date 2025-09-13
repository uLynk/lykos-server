package com.lykos.domain.entidade;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "contratacao")
public class Contratacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pacote_id", nullable = false)
    private Long pacoteId;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    // campos adicionados pela migration
    @Column(name = "valor_total", precision = 18, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "pacote_nome")
    private String pacoteNome;

    @Column(name = "pacote_preco", precision = 18, scale = 2)
    private BigDecimal pacotePreco;

    @Column(name = "pacote_descricao", length = 2000)
    private String pacoteDescricao;

    @Column(name = "comissao", precision = 18, scale = 2)
    private BigDecimal comissao;

    @Column(name = "valor_liquido", precision = 18, scale = 2)
    private BigDecimal valorLiquido;

    @Column(name = "pagamento_processado")
    private boolean pagamentoProcessado = false;

    @Column(name = "disputa_aberta")
    private boolean disputaAberta = false;

    @Column(name = "liberado")
    private boolean liberado = false;

    @Column(name = "criado_em")
    private OffsetDateTime criadoEm;

    @Version
    @Column(name = "versao")
    private Long versao;

   
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPagamento status;

    protected Contratacao() {}

    public Contratacao(Long pacoteId, Long clienteId, BigDecimal valorTotal) {
        this.pacoteId = pacoteId;
        this.clienteId = clienteId;
        this.valorTotal = valorTotal;
        this.criadoEm = OffsetDateTime.now();
    }

    public enum StatusPagamento {
        PENDENTE,
        PAGO,
        CANCELADO,
        ESTORNADO
    }

    //getters e setters relevantes

    public Long getId() { return id; }
    public Long getPacoteId() { return pacoteId; }
    public Long getClienteId() { return clienteId; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public String getPacoteNome() { return pacoteNome; }
    public void setPacoteNome(String pacoteNome) { this.pacoteNome = pacoteNome; }

    public BigDecimal getPacotePreco() { return pacotePreco; }
    public void setPacotePreco(BigDecimal pacotePreco) { this.pacotePreco = pacotePreco; }

    public String getPacoteDescricao() { return pacoteDescricao; }
    public void setPacoteDescricao(String pacoteDescricao) { this.pacoteDescricao = pacoteDescricao; }

    public BigDecimal getComissao() { return comissao; }
    public void setComissao(BigDecimal comissao) { this.comissao = comissao; }

    public BigDecimal getValorLiquido() { return valorLiquido; }
    public void setValorLiquido(BigDecimal valorLiquido) { this.valorLiquido = valorLiquido; }

    public boolean isPagamentoProcessado() { return pagamentoProcessado; }
    public void setPagamentoProcessado(boolean pagamentoProcessado) { this.pagamentoProcessado = pagamentoProcessado; }

    public boolean isDisputaAberta() { return disputaAberta; }
    public void setDisputaAberta(boolean disputaAberta) { this.disputaAberta = disputaAberta; }

    public boolean isLiberado() { return liberado; }
    public void setLiberado(boolean liberado) { this.liberado = liberado; }

    public OffsetDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(OffsetDateTime criadoEm) { this.criadoEm = criadoEm; }

    public StatusPagamento getStatus() { return status; }
    public void setStatus(StatusPagamento status) { this.status = status; }
}
