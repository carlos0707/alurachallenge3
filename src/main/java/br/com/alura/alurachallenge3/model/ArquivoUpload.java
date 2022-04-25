package br.com.alura.alurachallenge3.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class ArquivoUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate dataImportacao;
    private LocalDate dataTransacoes;

    @OneToMany(mappedBy = "arquivoUpload", cascade = CascadeType.ALL)
    private List<Transacao> transacoes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public LocalDate getDataImportacao() {
        return dataImportacao;
    }

    public void setDataImportacao(LocalDate dataImportacao) {
        this.dataImportacao = dataImportacao;
    }

    public LocalDate getDataTransacoes() {
        return dataTransacoes;
    }

    public void setDataTransacoes(LocalDate dataTransacoes) {
        this.dataTransacoes = dataTransacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    @Override
    public String toString() {
        return "ArquivoUpload{" +
                "id=" + id +
                ", dataImportacao=" + dataImportacao +
                ", dataTransacoes=" + dataTransacoes +
                '}';
    }
}
