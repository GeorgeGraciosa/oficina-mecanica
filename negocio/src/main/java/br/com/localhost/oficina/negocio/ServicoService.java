package br.com.localhost.oficina.negocio;

import br.com.localhost.oficina.modelo.Servico;
import br.com.localhost.oficina.modelo.ServicoItem;
import br.com.localhost.oficina.modelo.Veiculo;
import br.com.localhost.oficina.modelo.enums.TipoItemServico;

import java.math.BigDecimal;
import java.util.Date;

public class ServicoService {

    public Servico criarServico(Veiculo veiculo, String descricao) throws Exception {
        if(veiculo == null) {
            throw new Exception("Veículo não pode ser nulo");
        }

        Servico servico = new Servico();
        servico.setVeiculo(veiculo);
        servico.setDescricao(descricao);
        servico.setDataInicio(new Date());

        return servico;
    }

    public void adicionarItem(Servico servico, ServicoItem item) throws Exception {
        if(servico == null) {
            throw new Exception("O serviço não pode ser nulo.");
        }

        if(item == null) {
            throw new Exception("O item não pode ser nulo.");
        }

        if(servico.getDataFim() != null) {
            throw new Exception("O serviço já foi finalizado.");
        }

        servico.getItens().add(item);
    }

    public BigDecimal calcularValor(Servico servico) throws Exception {
        if(servico == null) {
            throw new Exception("Serviço é nulo.");
        }

        if(servico.getItens().isEmpty()) {
            throw new Exception("Lista vazia, não há nada para calcular");
        }

        BigDecimal soma = BigDecimal.ZERO;


        for( int i = 0; i < servico.getItens().size(); i++ ) {

            ServicoItem item = servico.getItens().get(i);

            if(item == null) {
                throw new  Exception("Nenhum item encontrado.");
            }

            if (item.getTipo() == null) {
                throw new Exception("O tipo do item é obrigatório.");
            }

            BigDecimal quantidade = BigDecimal.valueOf(item.getQuantidade());

            if(quantidade.compareTo(BigDecimal.ZERO) <= 0) {
                throw new Exception("A quantidade deve ser maior que zero.");
            }

            BigDecimal valorItem = BigDecimal.ZERO;

            if(item.getTipo().equals(TipoItemServico.PECA)){
                if (item.getPeca() == null) {
                    throw new Exception("A peça do item é obrigatória.");
                }
                if (item.getPeca().getValor().compareTo(BigDecimal.ZERO) < 1) {
                    throw new Exception("O valor não pode ser zero ou negativo");
                }
                if (item.getPeca().getValor() == null) {
                    throw new Exception("O valor da peça é obrigatório.");
                }

                valorItem = item.getPeca().getValor();
            }
            if(item.getTipo().equals(TipoItemServico.SERVICO)){
                if(item.getTipoServico() == null) {
                    throw new Exception("O tipo de serviço do item é obrigatório.");
                }
                if (item.getTipoServico().getValor().compareTo(BigDecimal.ZERO) < 1) {
                    throw new Exception("O valor não pode ser zero ou negativo.");
                }
                if (item.getTipoServico().getValor() == null) {
                    throw new Exception("O valor do tipo de serviço é obrigatório.");
                }

                valorItem = item.getTipoServico().getValor();
            }

            BigDecimal valorTotal = valorItem.multiply(quantidade);
            soma = soma.add(valorTotal);
        }
        return soma;
    }

    public void finalizar(Servico servico) throws Exception {
        if(servico == null) {
            throw new Exception("Erro, serviço não pode ser nulo");
        }
        if(servico.getDataFim() != null){
            throw new Exception("Esse serviço já foi finalizado");
        }

        BigDecimal valorFinal = calcularValor(servico);

        servico.setValor(valorFinal);

        servico.setDataFim(new Date());

    }
}
