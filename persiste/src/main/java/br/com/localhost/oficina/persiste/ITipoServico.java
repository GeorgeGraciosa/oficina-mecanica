package br.com.localhost.oficina.persiste;

import br.com.localhost.oficina.modelo.TipoServico;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author treinamento
 */
public interface ITipoServico {
    
    public int manter(TipoServico tipoServico) throws SQLException;
    
    public TipoServico obter(String codigo) throws SQLException;
    
    public List<TipoServico> obterLista(String codigo, String descricao, BigDecimal valor) throws SQLException;
}
