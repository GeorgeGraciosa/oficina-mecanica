package br.com.localhost.oficina.negocio;

import br.com.localhost.oficina.modelo.Peca;

import java.math.BigDecimal;

public class PecaService {
    public Peca criarPeca(String codigo, String descricao, BigDecimal valor) throws Exception {
        if(codigo == null|| codigo.isBlank()){
            throw new Exception("O código da peça é obrigatório.");
        }
        if(descricao == null || descricao.isBlank()){
            throw new Exception("Por favor insira uma descrição.");
        }
        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new Exception("Insira um valor válido.");
        }

        Peca novaPeca = new Peca();

        novaPeca.setCodigo(codigo.trim());
        novaPeca.setDescricao(descricao.trim());
        novaPeca.setValor(valor);

        return novaPeca;
    }
}
