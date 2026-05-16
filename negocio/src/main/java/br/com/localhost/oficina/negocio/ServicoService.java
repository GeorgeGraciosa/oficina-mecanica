package br.com.localhost.oficina.negocio;

import br.com.localhost.oficina.modelo.Servico;
import br.com.localhost.oficina.modelo.ServicoItem;
import br.com.localhost.oficina.modelo.enums.TipoItemServico;

import java.math.BigDecimal;
import java.util.Date;

public class ServicoService {

    public Servico criarServico(br.com.localhost.oficina.modelo.Veiculo veiculo, String descricao) throws Exception {
        if(veiculo == null) {
            throw new Exception("Veículo não pode ser nulo");
        }

        br.com.localhost.oficina.modelo.Servico servico = new br.com.localhost.oficina.modelo.Servico();
        servico.setVeiculo(veiculo);
        servico.setDescricao(descricao);
        servico.setDataInicio(new Date());

        return servico;
    }

    public void adicionarItem(br.com.localhost.oficina.modelo.Servico servico, br.com.localhost.oficina.modelo.ServicoItem item) throws Exception {
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

    public BigDecimal calcularValor(br.com.localhost.oficina.modelo.Servico servico) throws Exception {
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

            if (item.getPeca() == null) {
                throw new Exception("A peça do item é obrigatória.");
            }

            if(item.getTipoServico() == null) {
                throw new Exception("O tipo de serviço do item é obrigatório.");
            }

            BigDecimal valorPeca = item.getPeca().getValor();
            BigDecimal valorServico = item.getTipoServico().getValor();

            if(item.getTipo().equals(TipoItemServico.PECA)){
                if (valorPeca.compareTo(BigDecimal.ZERO) < 1) {
                    throw new Exception("O valor não pode ser zero ou negativo");
                }
                soma = soma.add(valorPeca);
            }
            if(item.getTipo().equals(TipoItemServico.SERVICO)){
                if (valorServico.compareTo(BigDecimal.ZERO) < 1) {
                    throw new Exception("O valor não pode ser zero ou negativo");
                }
                soma = soma.add(valorServico);
            }
        }
        return soma;
    }

    public void finalizar(br.com.localhost.oficina.modelo.Servico servico) throws Exception {
        if(servico.getDataFim() != null){
            throw new Exception("Esse serviço já foi finalizado");
        }

        servico.setDataFim(new Date());

    }
}
