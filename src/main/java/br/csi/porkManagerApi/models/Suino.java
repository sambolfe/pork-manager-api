package br.csi.porkManagerApi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "suino")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Suino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "raca_id", nullable = false)
    private Raca raca;

    @Column(name = "identificacao_orelha", nullable = false)
    @NotBlank
    @NotNull
    private String identificacaoOrelha;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipo_suino", nullable = false)
    @NotNull
    private TipoSuino tipoSuino;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    @NotNull
    private SexoSuino sexo;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_nasc", nullable = false)
    @NotNull
    private Date dataNasc;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "alojamento_id", nullable = false)
    private Alojamento alojamento;

    @CreatedDate
    @Temporal(TemporalType.DATE)
    @Column(name = "criado_em", nullable = false)
    @NotNull
    private Date criadoEm;

    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    @Column(name = "atualizado_em", nullable = false)
    @NotNull
    private Date atualizadoEm;

    @ManyToOne
    @JsonBackReference
    private Usuario usuario;
    @OneToMany(mappedBy = "suino", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Saude> saudes;

    public enum TipoSuino {
        GESTACAO,
        CRECHE,
        ENGORDA
    }

    public enum SexoSuino {
        MACHO,
        FEMEA
    }
}
