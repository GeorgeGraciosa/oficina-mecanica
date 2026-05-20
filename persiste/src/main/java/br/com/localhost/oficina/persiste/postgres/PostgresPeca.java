package br.com.localhost.oficina.persiste.postgres;

import br.com.localhost.oficina.conexao.bd.Conexao;
import br.com.localhost.oficina.modelo.Peca;
import br.com.localhost.oficina.persiste.IPeca;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author treinamento
 */
public class PostgresPeca implements IPeca{
    private final Conexao conexao;

    public PostgresPeca(Conexao conexao) {
        this.conexao = conexao;
    }
    
    private StringBuilder montarSQL(){
        StringBuilder textoSQL = new StringBuilder();
        textoSQL.append("SELECT ");
        textoSQL.append("codigo, ");
        textoSQL.append("descricao, ");
        textoSQL.append("valor ");
        textoSQL.append("from george.peca ");
        return textoSQL;
    }
    
    private Peca montarItem(ResultSet resultSet) throws SQLException{
        Peca peca = new Peca();
        peca.setCodigo(resultSet.getString("codigo"));
        peca.setDescricao(resultSet.getString("descricao"));
        peca.setValor(resultSet.getBigDecimal("valor"));
        return peca;
    }
    
    private List<Peca> montarLista(ResultSet resultSet) throws SQLException{
        List<Peca> pecas = new ArrayList<>();
        while(resultSet.next()){
            pecas.add(montarItem(resultSet));
        }
        return pecas;
    }

    @Override
    public int manter(Peca peca) throws SQLException {
        StringBuilder textoSQL = new StringBuilder();
        textoSQL.append("INSERT INTO george.peca");
        textoSQL.append("(id_peca, descricao_peca, valorpeca)");
        textoSQL.append("VALUES(?, ?, ?) ");
        
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            comando.setString(1, peca.getCodigo());
            comando.setString(2, peca.getDescricao());
            comando.setBigDecimal(3, peca.getValor());
            return comando.executeUpdate();
        }
    }

    @Override
    public Peca obter(String codigo) throws SQLException {
        StringBuilder textoSQL = montarSQL();
        textoSQL.append(" WHERE codigo = ? ");
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            comando.setString(1, codigo);
            try(ResultSet result = comando.executeQuery()){
                return result.next() ? montarItem(result): null;
            }
        }
    }

    @Override
    public List<Peca> obterLista(String codigo, String descricao, BigDecimal valor) throws SQLException {
        StringBuilder textoSQL = montarSQL();
        boolean where = codigo != null || descricao != null || valor != null;
        if(where){
            textoSQL.append(" WHERE ");
            String delimitador = "";
            
            if(codigo != null){
                textoSQL.append(delimitador).append("codigo = ? ");
                delimitador = " AND ";
            }
            
            if(descricao != null){
                textoSQL.append(delimitador).append("descricao = ? ");
                delimitador = " AND ";
            }
            
            if(valor != null){
                textoSQL.append(delimitador).append("valor =? ");
                delimitador = " AND ";
            }
        }
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            int count = 0;
            if(codigo != null){
                comando.setString(++count, codigo);
            }
            if(descricao != null){
                comando.setString(++count, descricao);
            }
            if(valor != null){
                comando.setBigDecimal(++count, valor);
            }
            try(ResultSet result = comando.executeQuery()){
                return montarLista(result);
            }
        }
    }
    
}
