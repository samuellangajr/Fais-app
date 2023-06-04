package mz.ac.isutc.lecc31.fais.Samuel.models;

import java.io.Serializable;

public class Evento implements Serializable {
    private String id_evento;
    private String id_promotor;
    private String image_url;
    private String nome;
    private String data;
    private String local;
    private String hora;
    private String descricao;
    private String categoria;
    private String imagem;
    private String preco_normal;
    private String preco_vip;
    private String quant_normal;
    private String quant_vip;

    public Evento() {

    }

    public String getId_evento() {
        return id_evento;
    }

    public void setId_evento(String id_evento) {
        this.id_evento = id_evento;
    }

    public String getId_promotor() {
        return id_promotor;
    }

    public void setId_promotor(String id_promotor) {
        this.id_promotor = id_promotor;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getPreco_normal() {
        return preco_normal;
    }

    public void setPreco_normal(String preco_normal) {
        this.preco_normal = preco_normal;
    }

    public String getPreco_vip() {
        return preco_vip;
    }

    public void setPreco_vip(String preco_vip) {
        this.preco_vip = preco_vip;
    }

    public String getQuant_normal() {
        return quant_normal;
    }

    public void setQuant_normal(String quant_normal) {
        this.quant_normal = quant_normal;
    }

    public String getQuant_vip() {
        return quant_vip;
    }

    public void setQuant_vip(String quant_vip) {
        this.quant_vip = quant_vip;
    }
}