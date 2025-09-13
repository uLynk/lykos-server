package com.lykos.integracao;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class MockPagamentoGateway implements PagamentoGateway {
    @Override
    public boolean transferir(String contaDestino, BigDecimal valor) {
        System.out.println("MockPagamentoGateway: transferindo " + valor + " para " + contaDestino);
        return true;
    }
}
