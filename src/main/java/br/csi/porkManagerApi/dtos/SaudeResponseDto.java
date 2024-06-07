package br.csi.porkManagerApi.dtos;

import java.math.BigDecimal;
import java.util.Date;

public class SaudeResponseDto {
    private Long id;
    private BigDecimal peso;
    private Date dataEntradaCio;
    private String tipoTratamento;
    private Date dataInicioTratamento;
    private String observacoes;
    private Date criadoEm;
    private Date atualizadoEm;
    private String identificadorOrelha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Date getDataEntradaCio() {
        return dataEntradaCio;
    }

    public void setDataEntradaCio(Date dataEntradaCio) {
        this.dataEntradaCio = dataEntradaCio;
    }

    public String getTipoTratamento() {
        return tipoTratamento;
    }

    public void setTipoTratamento(String tipoTratamento) {
        this.tipoTratamento = tipoTratamento;
    }

    public Date getDataInicioTratamento() {
        return dataInicioTratamento;
    }

    public void setDataInicioTratamento(Date dataInicioTratamento) {
        this.dataInicioTratamento = dataInicioTratamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Date getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Date criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Date getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Date atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public String getIdentificadorOrelha() {
        return identificadorOrelha;
    }

    public void setIdentificadorOrelha(String identificadorOrelha) {
        this.identificadorOrelha = identificadorOrelha;
    }
}