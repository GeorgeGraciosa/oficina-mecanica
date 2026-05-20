package br.com.localhost.oficina.persiste;

import br.com.localhost.oficina.modelo.Pessoa;
import java.util.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author treinamento
 */
public interface IPessoa {
    
    public int manter(Pessoa pessoa) throws SQLException;
    
    public Pessoa obter(UUID id) throws SQLException;
    
    public List<Pessoa> obterLista(UUID id, String nomecompleto, String cpfCnpj, Date dataNascimento) throws SQLException;
}
