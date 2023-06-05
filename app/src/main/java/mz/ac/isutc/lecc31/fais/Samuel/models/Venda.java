package mz.ac.isutc.lecc31.fais.Samuel.models;

public class Venda {
    private String id_ticket;
    private String evento;
    private String id_comprador;
    private String id_evento;
    private String nome;
    private String data_compra;
    private String hora_compra;
    private String metodo;
    private String tipo_ticket;
    private String valor;
    private String celular;
    private String email;

    private String estado;
    public Venda() {

    }
    public String getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(String id_ticket) {
        this.id_ticket= id_ticket;
    }

    public String getId_comprador() {
        return id_comprador;
    }

    public void setId_comprador(String id_comprador) {
        this.id_comprador = id_comprador;
    }

    public String getId_evento() {
        return id_evento;
    }

    public void setId_evento(String id_evento) {
        this.id_evento = id_evento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData_compra() {
        return data_compra;
    }

    public void setData_compra(String data_compra) {
        this.data_compra = data_compra;
    }

    public String getHora_compra() {
        return hora_compra;
    }

    public void setHora_compra(String hora_compra) {
        this.hora_compra = hora_compra;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getTipo_ticket() {
        return tipo_ticket;
    }

    public void setTipo_ticket(String tipo_ticket) {
        this.tipo_ticket = tipo_ticket;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
