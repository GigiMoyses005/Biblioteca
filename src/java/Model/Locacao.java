package Model;

import java.util.Date;

public class Locacao {
    private int id;
    private int idPessoa;
    private int idLivro;
    private Date dataInicio;
    private Date dataFim;
    private Date dataLocacao;
    private String status; // "ativa", "devolvida", "atrasada"

    // Construtor padrão
    public Locacao() {}

    // Construtor com parâmetros
    public Locacao(int idPessoa, int idLivro, Date dataInicio, Date dataFim) {
        this.idPessoa = idPessoa;
        this.idLivro = idLivro;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = "ativa";
        this.dataLocacao = new Date();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(Date dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
} 