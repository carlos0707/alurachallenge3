package br.com.alura.alurachallenge3.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String bancoOrigem;
    private String agenciaOrigem;
    private String contaOrigem;
    private String bancoDestino;
    private String agenciaDestino;
    private String contaDestino;
    private BigDecimal valor;
    private LocalDate dataTransacao;
    private String horaTransacao;

    @JoinColumn(name = "arquivo_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private ArquivoUpload arquivoUpload;

    public String getBancoOrigem() {
        return bancoOrigem;
    }

    public void setBancoOrigem(String bancoOrigem) {
        this.bancoOrigem = bancoOrigem;
    }

    public String getAgenciaOrigem() {
        return agenciaOrigem;
    }

    public void setAgenciaOrigem(String agenciaOrigem) {
        this.agenciaOrigem = agenciaOrigem;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(String contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public String getBancoDestino() {
        return bancoDestino;
    }

    public void setBancoDestino(String bancoDestino) {
        this.bancoDestino = bancoDestino;
    }

    public String getAgenciaDestino() {
        return agenciaDestino;
    }

    public void setAgenciaDestino(String agenciaDestino) {
        this.agenciaDestino = agenciaDestino;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(String contaDestino) {
        this.contaDestino = contaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(LocalDate dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public String getHoraTransacao() {
        return horaTransacao;
    }

    public void setHoraTransacao(String horaTransacao) {
        this.horaTransacao = horaTransacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArquivoUpload getArquivoUpload() {
        return arquivoUpload;
    }

    public void setArquivoUpload(ArquivoUpload arquivoUpload) {
        this.arquivoUpload = arquivoUpload;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "bancoOrigem='" + bancoOrigem + '\'' +
                ", agenciaOrigem='" + agenciaOrigem + '\'' +
                ", contaOrigem='" + contaOrigem + '\'' +
                ", bancoDestino='" + bancoDestino + '\'' +
                ", agenciaDestino='" + agenciaDestino + '\'' +
                ", contaDestino='" + contaDestino + '\'' +
                ", valor=" + valor +
                ", dataTransacao=" + dataTransacao +
                ", horaTransacao='" + horaTransacao + '\'' +
                '}';
    }
}
