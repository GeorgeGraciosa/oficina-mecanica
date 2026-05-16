package br.com.localhost.oficina.modelo;

import br.com.localhost.oficina.modelo.enums.TipoItemServico;

public class ServicoItem {
    private Servico servico;
    private TipoItemServico tipo;
    private Peca peca;
    private TipoServico tipoServico;
    private int quantidade;

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public TipoItemServico getTipo() {
        return tipo;
    }

    public void setTipo(TipoItemServico tipo) {
        this.tipo = tipo;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }
}
