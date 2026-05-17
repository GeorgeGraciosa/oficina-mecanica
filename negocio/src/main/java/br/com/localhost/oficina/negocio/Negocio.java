package br.com.localhost.oficina.negocio;

import br.com.localhost.oficina.modelo.*;
import br.com.localhost.oficina.modelo.enums.TipoItemServico;
import br.com.localhost.oficina.modelo.enums.TipoVeiculo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Negocio {
    public static void main(String[] args) throws Exception {
        PessoaService pessoaService = new PessoaService();
        VeiculoService veiculoService = new VeiculoService();
        PecaService pecaService = new PecaService();
        TipoServicoService tipoServicoService = new TipoServicoService();
        ServicoService servicoService = new ServicoService();
        Date dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000");

        Pessoa pessoa = pessoaService.criarPessoa("GeorgeGraciosa",dataNascimento,"11417925914");
        Veiculo veiculo = veiculoService.criarVeiculo("ABC123", pessoa, TipoVeiculo.CARRO);
        Peca peca = pecaService.criarPeca("AA02", "Oleo do motor", BigDecimal.TEN);
        TipoServico tipoServico = tipoServicoService.criarTipoServico("BB030", "Troca de bateria", BigDecimal.TEN);

        Servico servico = servicoService.criarServico(veiculo, "troca de óleo completa");
        ServicoItem itemPeca = new ServicoItem();
        itemPeca.setServico(servico);
        itemPeca.setTipo(TipoItemServico.PECA);
        itemPeca.setPeca(peca);
        itemPeca.setQuantidade(3);

        ServicoItem itemServico = new ServicoItem();
        itemServico.setServico(servico);
        itemServico.setTipo(TipoItemServico.SERVICO);
        itemServico.setTipoServico(tipoServico);
        itemServico.setQuantidade(2);

        servicoService.adicionarItem(servico, itemPeca);
        servicoService.adicionarItem(servico, itemServico);

        servicoService.calcularValor(servico);

        servicoService.finalizar(servico);

        System.out.println(servico.getValor());
        System.out.println("Servico finalizado com sucesso!");
    }
}
