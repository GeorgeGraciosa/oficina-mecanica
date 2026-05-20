package br.com.localhost.oficina.persiste.postgres;

import br.com.localhost.oficina.conexao.bd.Conexao;
import br.com.localhost.oficina.modelo.TipoServico;
import br.com.localhost.oficina.persiste.ITipoServico;
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
public class PostgresTipoServico implements ITipoServico{
    
    private final Conexao conexao;

    public PostgresTipoServico(Conexao conexao) {
        this.conexao = conexao;
    }
    
    private StringBuilder montarSQL(){
        StringBuilder textoSQL = new StringBuilder();
        textoSQL.append("SELECT");
        textoSQL.append("id_tiposervico, ");
        textoSQL.append("descricao_tiposervico, ");
        textoSQL.append("valor_tiposervico ");
        textoSQL.append("from george.tiposervico");
        return textoSQL;
    }
    
    private TipoServico montarItem(ResultSet resultSet) throws SQLException{
        TipoServico tipo = new TipoServico();
        tipo.setCodigo(resultSet.getString("id_tiposervico"));
        tipo.setDescricao(resultSet.getString("descricao_tiposervico"));
        tipo.setValor(resultSet.getBigDecimal("valor_tiposervico"));
        return tipo;
    }
    
    private List<TipoServico> montarLista(ResultSet resultSet) throws SQLException{
        List<TipoServico> tipos = new ArrayList<>();
        while(resultSet.next()){
            tipos.add(montarItem(resultSet));
        }
        return tipos;
    }

    @Override
    public int manter(TipoServico tipo) throws SQLException {
        StringBuilder textoSQL = new StringBuilder();
        textoSQL.append("INSERT INTO george.tiposervico");
        textoSQL.append("(id_tiposervico, descricao_tiposervico, valor_tiposervico)");
        textoSQL.append("VALUES(?, ?, ?) ");
        
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            comando.setString(1, tipo.getCodigo());
            comando.setString(2, tipo.getDescricao());
            comando.setBigDecimal(3, tipo.getValor());
            return comando.executeUpdate();
        }
    }

    @Override
    public TipoServico obter(String codigo) throws SQLException {
        StringBuilder textoSQL = montarSQL();
        textoSQL.append("WHERE codigo = ?");
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            comando.setString(1, codigo);
            try(ResultSet result = comando.executeQuery()){
                return result.next() ? montarItem(result): null;
            }
        }
    }

    @Override
    public List<TipoServico> obterLista(String codigo, String descricao, BigDecimal valor) throws SQLException {
        StringBuilder textoSQL = montarSQL();
        boolean where = codigo != null || descricao != null || valor != null;
        while(where){
            textoSQL.append("WHERE");
            String delimitador = "";
            
            if(codigo != null){
                textoSQL.append(delimitador).append("codigo = ? ");
                delimitador = " AND ";
            }
            
            if(descricao != null){
                textoSQL.append(delimitador).append("descricao = ?");
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
            }if(valor != null){
                comando.setBigDecimal(++count, valor);
            }
            try(ResultSet result = comando.executeQuery()){
                return montarLista(result);
            }
        }
    }
    
}
