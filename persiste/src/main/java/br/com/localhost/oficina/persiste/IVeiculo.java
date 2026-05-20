package br.com.localhost.oficina.persiste;

import br.com.localhost.oficina.modelo.Pessoa;
import br.com.localhost.oficina.modelo.Veiculo;
import br.com.localhost.oficina.modelo.enums.TipoVeiculo;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author treinamento
 */
public interface IVeiculo {
    public int manter(Veiculo veiculo) throws SQLException;
    
    public Veiculo obter(String placa) throws SQLException;
    
    public List<Veiculo> obterLista(String placa, Pessoa proprietario, TipoVeiculo tipo) throws SQLException;
    
}
