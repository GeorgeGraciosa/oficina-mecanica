package br.com.localhost.oficina.persiste;

import br.com.localhost.oficina.modelo.Peca;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author treinamento
 */
public interface IPeca {
    
    public int manter(Peca peca) throws SQLException;
    
    public Peca obter(String codigo) throws SQLException;
    
    public List<Peca> obterLista(String codigo, String descricao, BigDecimal valor) throws SQLException;    
}
