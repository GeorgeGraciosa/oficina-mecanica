package br.com.localhost.oficina.persiste.postgres;

import br.com.localhost.oficina.conexao.bd.Conexao;
import br.com.localhost.oficina.modelo.Pessoa;
import br.com.localhost.oficina.modelo.Veiculo;
import br.com.localhost.oficina.modelo.enums.TipoVeiculo;
import br.com.localhost.oficina.persiste.IVeiculo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author georg
 */
public class PostgresVeiculo implements IVeiculo{
    private final Conexao conexao;

    public PostgresVeiculo(Conexao conexao) {
        this.conexao = conexao;
    }
    
    private StringBuilder montarSQL(){
        StringBuilder textoSQL = new StringBuilder();
        textoSQL.append("SELECT ");
        textoSQL.append("placa, ");
        textoSQL.append("id_proprietario, ");
        textoSQL.append("tipo_veiculo ");
        textoSQL.append("from george.veiculo");
        return textoSQL;
    }
    
    private Veiculo montarItem(ResultSet resultSet) throws SQLException{
        Veiculo veiculo = new Veiculo();
        
        String tipo = resultSet.getString("tipo_veiculo");                
        veiculo.setPlaca(resultSet.getString("placa"));
        
        Pessoa proprietario = new Pessoa();
        proprietario.setId((UUID) resultSet.getObject("id_proprietario"));
        veiculo.setProprietario(proprietario);
        veiculo.setTipoVeiculo(TipoVeiculo.valueOf(tipo));
        
        return veiculo;
    }
    
    private List<Veiculo> montarLista(ResultSet resultSet) throws SQLException{
        List<Veiculo> veiculos = new ArrayList<>();
        while(resultSet.next()){
            veiculos.add(montarItem(resultSet));
        }
        return veiculos;
    }

    @Override
    public int manter(Veiculo veiculo) throws SQLException {
        StringBuilder textoSQL = new StringBuilder();
        textoSQL.append("INSERT INTO george.veiculo ");
        textoSQL.append("(placa, id_proprietario, tipo_veiculo ) ");
        textoSQL.append("VALUES(?, ?, ?) ");
        
        
        
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            comando.setString(1, veiculo.getPlaca());
            comando.setObject(2, veiculo.getProprietario().getId(), Types.OTHER);
            comando.setString(3, veiculo.getTipoVeiculo().name());
            return comando.executeUpdate();
        }
    }

    @Override
    public Veiculo obter(String placa) throws SQLException {
        StringBuilder textoSQL = montarSQL();
        textoSQL.append(" WHERE placa = ? ");
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            comando.setString(1, placa);
            try(ResultSet result = comando.executeQuery()){
                return result.next() ? montarItem(result): null;
            }
        }
    }

    @Override
    public List<Veiculo> obterLista(String placa, Pessoa proprietario, TipoVeiculo tipoVeiculo) throws SQLException {
        StringBuilder textoSQL = montarSQL();
        boolean where = placa != null || proprietario != null || tipoVeiculo != null;
        if(where){
            textoSQL.append(" WHERE ");
            String delimitador = "";
            
            if(placa != null){
                textoSQL.append(delimitador).append("placa = ? ");
                delimitador = " AND ";
            }
            
            if(proprietario != null){
                textoSQL.append(delimitador).append("id_proprietario = ? ");
                delimitador = " AND ";
            }
            
            if(tipoVeiculo != null){
                textoSQL.append(delimitador).append("tipo_veiculo = ? ");
                delimitador = " AND ";
            }
        }
        try(PreparedStatement comando = conexao.getConnection().prepareStatement(textoSQL.toString())){
            int count = 0;
            if(placa != null){
                comando.setString(++count, placa);
            }
            if(proprietario != null){
                comando.setObject(++count, proprietario.getId(), Types.OTHER);
            }
            if(tipoVeiculo != null){
                comando.setString(++count, tipoVeiculo.name());
            }
            try(ResultSet result = comando.executeQuery()){
                return montarLista(result);
            }
        }
    }
    
}
