package br.edu.ifma.crea_ma.modelo;

import java.io.Serializable;
import java.sql.Blob;


public class Notificacao implements Serializable{
    private Long id;
    private String nomeNotificado;
    private String dadosObra;
    private String infracao;
    private String alvenaria;
    private String fundacao;
    private String cobertura;
    private String instalacao;
    private String caminhoFoto;
    private String enderecoNotificacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeNotificado() {
        return nomeNotificado;
    }

    public void setNomeNotificado(String nomeNotificado) {
        this.nomeNotificado = nomeNotificado;
    }

    public String getDadosObra() {
        return dadosObra;
    }

    public void setDadosObra(String dadosObra) {
        this.dadosObra = dadosObra;
    }

    public String getInfracaoCometida() {
        return infracao;
    }

    public void setInfracaoCometida(String infracao) {
        this.infracao = infracao;
    }

    public String getInstalacao() {
        return instalacao;
    }

    public void setInstalacao(String instalacao) {
        this.instalacao = instalacao;
    }

    public String getAlvenaria() {
        return alvenaria;
    }

    public void setAlvenaria(String alvenaria) {
        this.alvenaria = alvenaria;
    }

    public String getFundacao() {
        return fundacao;
    }

    public void setFundacao(String fundacao) {
        this.fundacao = fundacao;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public String getEnderecoNotificacao() {
        return enderecoNotificacao;
    }

    public void setEnderecoNotificacao(String enderecoNotificacao) {
        this.enderecoNotificacao = enderecoNotificacao;
    }

    @Override
    public String toString() {
        return getId() +" - "+ getNomeNotificado()+ " Infração: "+getInfracaoCometida();
    }


}
