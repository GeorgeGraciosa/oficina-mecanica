package br.com.localhost.oficina.persiste.postgres;

import br.com.localhost.oficina.conexao.bd.Conexao;
import br.com.localhost.oficina.modelo.Pessoa;
import br.com.localhost.oficina.persiste.IPessoa;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author treinamento
 */
public class PostgresPessoa implements IPessoa {
    
    private final Conexao conexao;

    public PostgresPessoa(Conexao conexao) {
        this.conexao = conexao;
    }
    
    private StringBuilder montarSQL(){
        StringBuilder textoSQL = new StringBuilder();
        textoSQL.append("SELECT ");
        textoSQL.append("id_pessoa, ");
        textoSQL.append("nome_completo, ");
        textoSQL.append("data_nascimento, ");
        textoSQL.append("cpf_cnpj, ");
        textoSQL.append("from george.pessoa ");
        return textoSQL;
    }
    
    private Pessoa montarItem(ResultSet resultSet) throws SQLException{
        Pessoa pessoa = new Pessoa();
        pessoa.setId((UUID) resultSet.getObject("id_pessoa"));
        pessoa.setNomeCompleto(resultSet.getString("nome_completo"));
        pessoa.setDataNascimento(resultSet.getDate("data_nascimento"));
        pessoa.setCpfCnpj(resultSet.getString("cpf_cnpj"));
        return pessoa;
    }
    
    private List<Pessoa> montarLista(ResultSet resultSet) throws SQLException{
        List<Pessoa> pessoas = new ArrayList<>();
        while(resultSet.next()){
            pessoas.add(montarItem(resultSet));
        }
        return pessoas;
    }

    @Override
    public int manter(Pessoa pessoa) throws SQLException {
        StringBuilder textoSQL = new StringBuilder();
        textoSQL.append("INSERT INTO george.pessoa ");
        textoSQL.append("(id_pessoa, nome_completo, data_nascimento, cpf_cnpj ) ");
        textoSQL.append("VALUES(?, ?, ?, ?) ");
        
        
        
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            comando.setObject(1, pessoa.getId(), Types.OTHER);
            comando.setString(2, pessoa.getNomeCompleto());
            comando.setDate(3, new java.sql.Date(pessoa.getDataNascimento().getTime()));
            comando.setString(4, pessoa.getCpfCnpj());
            return comando.executeUpdate();
        }
    }

    @Override
    public Pessoa obter(UUID id) throws SQLException {
        StringBuilder textoSQL = montarSQL();
        textoSQL.append(" WHERE id_pessoa = ? ");
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            comando.setObject(1, id, Types.OTHER);
            try(ResultSet result = comando.executeQuery()){
                return result.next() ? montarItem(result): null;
            }
        }
    }

    @Override
    public List<Pessoa> obterLista(UUID id, String nomeCompleto, String cpfCnpj, Date dataNascimento) throws SQLException {
        StringBuilder textoSQL = montarSQL();
        boolean where = id != null || nomeCompleto != null || cpfCnpj != null || dataNascimento != null;
        if(where){
            textoSQL.append(" WHERE ");
            String delimitador = "";
            
            if(id != null){
                textoSQL.append(delimitador).append("id_pessoa = ? ");
                delimitador = " AND ";
            }
            
            if(nomeCompleto != null){
                textoSQL.append(delimitador).append("nome_completo = ? ");
                delimitador = " AND ";
            }
            
            if(cpfCnpj != null){
                textoSQL.append(delimitador).append("cpf_cnpj = ? ");
                delimitador = " AND ";
            }
            if(dataNascimento != null){
                textoSQL.append(delimitador).append("data_nascimento = ? ");
                delimitador = " AND ";
            }
        }
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            int count = 0;
            if(id != null){
                comando.setObject(++count, id, Types.OTHER);
            }
            if(nomeCompleto != null){
                comando.setString(++count, nomeCompleto);
            }
            if(cpfCnpj != null){
                comando.setString(++count, cpfCnpj);
            }
            if(dataNascimento != null){
                comando.setDate(++count, new java.sql.Date(dataNascimento.getTime()));
            }
            try(ResultSet result = comando.executeQuery()){
                return montarLista(result);
            }
        }
    }
    
}
