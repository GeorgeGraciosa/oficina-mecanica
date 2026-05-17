package br.com.localhost.oficina.negocio;

import br.com.localhost.oficina.modelo.Pessoa;

import java.util.Date;
import java.util.UUID;

public class PessoaService {
    public Pessoa criarPessoa(String nomeCompleto, Date dataNascimento, String cpfCnpj) throws Exception {
        if(nomeCompleto == null || nomeCompleto.isBlank()) {
            throw new Exception("O valor de nome precisa ser informado.");
        }
        if(dataNascimento == null) {
            throw new Exception("Data de nascimento precisa ser informada.");
        }
        if(dataNascimento.after(new Date())) {
            throw new Exception("Data inválida.");
        }
        if(cpfCnpj == null || cpfCnpj.isBlank()) {
            throw new Exception("O valor de cpfCnpj precisa ser informado.");
        }
        if(cpfCnpj.trim().length() > 14 || cpfCnpj.trim().length() < 11) {
            throw new Exception("Por favor insira um valor válido para o CPF / CNPJ");
        }

        UUID pessoaId = UUID.randomUUID();

        Pessoa novaPessoa = new Pessoa();

        novaPessoa.setId(pessoaId);
        novaPessoa.setNomeCompleto(nomeCompleto);
        novaPessoa.setDataNascimento(dataNascimento);
        novaPessoa.setCpfCnpj(cpfCnpj);

        return novaPessoa;
    }
}
