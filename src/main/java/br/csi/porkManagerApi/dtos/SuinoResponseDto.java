package br.csi.porkManagerApi.dtos;

import java.util.Date;

public class SuinoResponseDto {
    private Long id;
    private Long idRaca;
    private String nomeRaca;
    private String identificacaoOrelha;
    private String dataNasc;
    private String sexo;
    private String observacoes;
    private String tipoSuino;
    private Long idUsuario;
    private String nomeUsuario;

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    private Long alojamentoId;
    private String nomeAlojamento;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRaca() {
        return idRaca;
    }

    public void setIdRaca(Long idRaca) {
        this.idRaca = idRaca;
    }

    public String getNomeRaca() {
        return nomeRaca;
    }

    public void setNomeRaca(String nomeRaca) {
        this.nomeRaca = nomeRaca;
    }

    public String getIdentificacaoOrelha() {
        return identificacaoOrelha;
    }

    public void setIdentificacaoOrelha(String identificacaoOrelha) {
        this.identificacaoOrelha = identificacaoOrelha;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getTipoSuino() {
        return tipoSuino;
    }

    public void setTipoSuino(String tipoSuino) {
        this.tipoSuino = tipoSuino;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getAlojamentoId() {
        return alojamentoId;
    }

    public void setAlojamentoId(Long alojamentoId) {
        this.alojamentoId = alojamentoId;
    }

    public String getNomeAlojamento() {
        return nomeAlojamento;
    }

    public void setNomeAlojamento(String nomeAlojamento) {
        this.nomeAlojamento = nomeAlojamento;
    }
}
