package br.csi.porkManagerApi.dtos;

public class SuinoIdentificadorDto {
    private Long idSuino;
    private String identificadorOrelha;

    public SuinoIdentificadorDto(Long idSuino, String identificadorOrelha) {
        this.idSuino = idSuino;
        this.identificadorOrelha = identificadorOrelha;
    }

    public Long getIdSuino() {
        return idSuino;
    }

    public void setIdSuino(Long idSuino) {
        this.idSuino = idSuino;
    }

    public String getIdentificadorOrelha() {
        return identificadorOrelha;
    }

    public void setIdentificadorOrelha(String identificadorOrelha) {
        this.identificadorOrelha = identificadorOrelha;
    }
}