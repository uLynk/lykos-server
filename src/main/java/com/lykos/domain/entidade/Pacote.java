package com.lykos.domain.entidade;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pacote")
public class Pacote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, precision = 19, scale=2)
    private BigDecimal preco;

    @Column(nullable = false, name = "prazo_dias")
    private Integer prazoDias;

    @Column(length = 2000, nullable = false)
    private String descricao;

    @Version
    private Long versao;

    protected Pacote() { }

    private Pacote(Builder b) {
        this.nome = b.nome;
        this.preco = b.preco;
        this.prazoDias = b.prazoDias;
        this.descricao = b.descricao;
    }

    public static class Builder {
        private String nome;
        private BigDecimal preco;
        private Integer prazoDias;
        private String descricao;

        public Builder nome(String nome) { this.nome = nome; return this; }
        public Builder preco(BigDecimal preco) { this.preco = preco; return this; }
        public Builder prazoDias(Integer prazoDias) { this.prazoDias = prazoDias; return this; }
        public Builder descricao(String descricao) { this.descricao = descricao; return this; }

        public Pacote build() {
            if (nome == null || nome.isBlank()) {
                throw new IllegalArgumentException("Nome do pacote é obrigatório");
            }
            if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Preço deve ser maior que zero");
            }
            if (prazoDias == null || prazoDias <= 0) {
                throw new IllegalArgumentException("Prazo (em dias) deve ser maior que zero");
            }
            if (descricao == null || descricao.isBlank()) {
                throw new IllegalArgumentException("Descrição é obrigatória");
            }
            return new Pacote(this);
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }
    public Integer getPrazoDias() { return prazoDias; }
    public String getDescricao() { return descricao; }
    public Long getVersao() { return versao; }

    public void setPreco(java.math.BigDecimal preco) { this.preco = preco; }
    public void setPrazoDias(Integer prazoDias) { this.prazoDias = prazoDias; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getTitulo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTitulo'");
    }
}
