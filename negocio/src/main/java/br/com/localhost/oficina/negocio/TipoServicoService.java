package br.com.localhost.oficina.negocio;

import br.com.localhost.oficina.modelo.TipoServico;

import java.math.BigDecimal;

public class TipoServicoService {

    public TipoServico criarTipoServico(String codigo, String descricao, BigDecimal valor) throws Exception {
        if(codigo == null|| codigo.isBlank()){
            throw new Exception("O código do serviço é obrigatório.");
        }
        if(descricao == null || descricao.isBlank()){
            throw new Exception("Por favor insira uma descrição.");
        }
        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new Exception("Insira um valor válido.");
        }

        TipoServico novoTipoServico = new TipoServico();

        novoTipoServico.setCodigo(codigo.trim());
        novoTipoServico.setDescricao(descricao.trim());
        novoTipoServico.setValor(valor);

        return novoTipoServico;
    }
}
