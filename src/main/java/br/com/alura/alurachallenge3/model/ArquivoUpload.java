package br.com.alura.alurachallenge3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class ArquivoUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate dataImportacao;
    private LocalDate dataTransacoes;

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

    @Override
    public String toString() {
        return "ArquivoUpload{" +
                "id=" + id +
                ", dataImportacao=" + dataImportacao +
                ", dataTransacoes=" + dataTransacoes +
                '}';
    }
}
