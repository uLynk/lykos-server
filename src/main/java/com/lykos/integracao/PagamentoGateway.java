package com.lykos.integracao;

import java.math.BigDecimal;

public interface PagamentoGateway {
    boolean transferir(String contaDestino, BigDecimal valor);
}
