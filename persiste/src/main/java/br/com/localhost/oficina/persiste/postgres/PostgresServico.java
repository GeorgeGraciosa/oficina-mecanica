package br.com.localhost.oficina.persiste.postgres;

import br.com.localhost.oficina.conexao.bd.Conexao;
import br.com.localhost.oficina.modelo.Servico;
import br.com.localhost.oficina.modelo.Veiculo;
import br.com.localhost.oficina.persiste.IServico;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author treinamento
 */
public class PostgresServico implements IServico{
    private final Conexao conexao;

    public PostgresServico(Conexao conexao) {
        this.conexao = conexao;
    }
    
    private StringBuilder montarSQL(){
        StringBuilder textoSQL = new StringBuilder();
        textoSQL.append("SELECT ");
        textoSQL.append("descricao, ");
        textoSQL.append("numero, ");
        textoSQL.append("data_inicio, ");
        textoSQL.append("data_fim, ");
        textoSQL.append("valor, ");
        textoSQL.append("veiculo ");
        textoSQL.append("from george.servico ");
        return textoSQL;
    }
    
    private Servico montarItem(ResultSet resultSet) throws SQLException{
        Servico servico = new Servico();
        servico.setDescricao(resultSet.getString("descricao"));
        servico.setNumero(resultSet.getInt("numero"));
        servico.setDataInicio(resultSet.getDate("data_inicio"));
        servico.setDataFim(resultSet.getDate("data_fim"));
        servico.setValor(resultSet.getBigDecimal("valor"));
        
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(resultSet.getString("veiculo"));
        servico.setVeiculo(veiculo);
        
        return servico;
    }
    
    private List<Servico> montarLista(ResultSet resultSet) throws SQLException{
        List<Servico> servicos = new ArrayList<>();
        while(resultSet.next()){
            servicos.add(montarItem(resultSet));
        }
        return servicos;
    }

        @Override
    public int manter(Servico servico) throws SQLException {
        StringBuilder textoSQL = new StringBuilder();
        textoSQL.append("INSERT INTO george.servico ");
        textoSQL.append("(descricao, numero, data_inicio, data_fim, valor, veiculo ) ");
        textoSQL.append("VALUES(?, ?, ?, ?, ?, ?) ");
        
        
        
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            comando.setString(1, servico.getDescricao());
            comando.setInt(2, servico.getNumero());
            comando.setDate(3, new java.sql.Date(servico.getDataInicio().getTime()));
            if(servico.getDataFim() != null) {
                comando.setDate(4, new java.sql.Date(servico.getDataFim().getTime()));
            } else {
                comando.setNull(4, Types.DATE);
            }
            comando.setBigDecimal(5, servico.getValor());
            comando.setString(6, servico.getVeiculo().getPlaca());
            return comando.executeUpdate();
        }
    }

    @Override
    public Servico obter(int numero) throws SQLException {
        StringBuilder textoSQL = montarSQL();
        textoSQL.append(" WHERE numero = ? ");
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            comando.setInt(1, numero);
            try(ResultSet result = comando.executeQuery()){
                return result.next() ? montarItem(result): null;
            }
        }
    }

    @Override
    public List<Servico> obterLista(String descricao, int numero, Date dataInicio, Date dataFim, BigDecimal valor, Veiculo veiculo) throws SQLException {
        StringBuilder textoSQL = montarSQL();
        boolean where = descricao != null || numero != 0 || dataInicio != null || dataFim != null || valor != null || veiculo != null;
        if(where){
            textoSQL.append(" WHERE ");
            String delimitador = "";
            
            if(descricao != null){
                textoSQL.append(delimitador).append("descricao = ? ");
                delimitador = " AND ";
            }
            
            if(numero != 0){
                textoSQL.append(delimitador).append("numero = ? ");
                delimitador = " AND ";
            }
            
            if(dataInicio != null){
                textoSQL.append(delimitador).append("data_inicio = ? ");
                delimitador = " AND ";
            }
            if(dataFim != null){
                textoSQL.append(delimitador).append("data_fim = ? ");
                delimitador = " AND ";
            }
            if(valor != null){
                textoSQL.append(delimitador).append("valor = ? ");
                delimitador = " AND ";
            }
            if(veiculo != null){
                textoSQL.append(delimitador).append("veiculo = ? ");
                delimitador = " AND ";
            }
        }
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            int count = 0;
            if(descricao != null){
                comando.setString(++count, descricao);
            }
            if(numero != 0){
                comando.setInt(++count, numero);
            }
            if(dataInicio != null){
                comando.setDate(++count, new java.sql.Date(dataInicio.getTime()));
            }
            if(dataFim != null){
                comando.setDate(++count, new java.sql.Date(dataFim.getTime()));
            }
            if(valor != null){
                comando.setBigDecimal(++count, valor);
            }
            if(veiculo != null){
                comando.setString(++count, veiculo.getPlaca());
            }
            try(ResultSet result = comando.executeQuery()){
                return montarLista(result);
            }
        }
    }
}
