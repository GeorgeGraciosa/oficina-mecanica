package br.com.localhost.oficina.persiste;

import br.com.localhost.oficina.modelo.Servico;
import br.com.localhost.oficina.modelo.Veiculo;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
/**
 *
 * @author treinamento
 */
public interface IServico {
    
    public int manter(Servico servico) throws SQLException;
    
    public Servico obter(int numero)throws SQLException;
    
    public List<Servico> obterLista(String descricao, int numero, Date dataInicio, Date dataFim, BigDecimal valor, Veiculo veiculo)throws SQLException;
}
