package br.com.localhost.oficina.persiste;

import br.com.localhost.oficina.conexao.bd.Conexao;
import br.com.localhost.oficina.persiste.postgres.PostgresPeca;
import br.com.localhost.oficina.persiste.postgres.PostgresPessoa;
import br.com.localhost.oficina.persiste.postgres.PostgresServico;
import br.com.localhost.oficina.persiste.postgres.PostgresTipoServico;
import br.com.localhost.oficina.persiste.postgres.PostgresVeiculo;

public abstract class DaoFabrica {

    public static IPessoa criarPessoa(Conexao conexao) {
        if ("postgresql".equalsIgnoreCase(conexao.getProvedor())) {
            return new PostgresPessoa(conexao);
        }
        return null;
    }

    public static IVeiculo criarVeiculo(Conexao conexao) {
        if ("postgresql".equalsIgnoreCase(conexao.getProvedor())) {
            return new PostgresVeiculo(conexao);
        }
        return null;
    }

    public static IPeca criarPeca(Conexao conexao) {
        if ("postgresql".equalsIgnoreCase(conexao.getProvedor())) {
            return new PostgresPeca(conexao);
        }
        return null;
    }

    public static ITipoServico criarTipoServico(Conexao conexao) {
        if ("postgresql".equalsIgnoreCase(conexao.getProvedor())) {
            return new PostgresTipoServico(conexao);
        }
        return null;
    }

    public static IServico criarServico(Conexao conexao) {
        if ("postgresql".equalsIgnoreCase(conexao.getProvedor())) {
            return new PostgresServico(conexao);
        }
        return null;
    }
}