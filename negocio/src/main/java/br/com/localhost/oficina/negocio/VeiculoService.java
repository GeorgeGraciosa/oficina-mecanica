package br.com.localhost.oficina.negocio;


import br.com.localhost.oficina.modelo.Pessoa;
import br.com.localhost.oficina.modelo.Veiculo;
import br.com.localhost.oficina.modelo.enums.TipoVeiculo;

public class VeiculoService {
    public Veiculo criarVeiculo(String placa, Pessoa proprietario, TipoVeiculo tipo ) throws Exception {

        if(placa == null || placa.trim().isEmpty()) {
            throw new Exception("O valor da placa não pode ser nulo.");
        }
        if(proprietario == null) {
            throw new Exception("O proprietario precisa existir.");
        }
        if(tipo == null) {
            throw new Exception("O tipo do veículo não pode ser nulo.");
        }

        Veiculo novoVeiculo = new Veiculo();

        novoVeiculo.setPlaca(placa);
        novoVeiculo.setProprietario(proprietario);
        novoVeiculo.setTipoVeiculo(tipo);

        return novoVeiculo;
    }
}
