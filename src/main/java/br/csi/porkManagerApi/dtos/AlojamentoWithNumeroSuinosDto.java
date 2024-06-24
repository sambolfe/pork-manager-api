package br.csi.porkManagerApi.dtos;

import br.csi.porkManagerApi.models.Alojamento.StatusAlojamento;
import br.csi.porkManagerApi.models.Suino.TipoSuino;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlojamentoWithNumeroSuinosDto {
    private Long id;
    private String nome;
    private TipoSuino tipo;
    private Integer capacidade;
    private StatusAlojamento status;
    private int numeroSuinosAtual;
}
