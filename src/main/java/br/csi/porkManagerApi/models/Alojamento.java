package br.csi.porkManagerApi.models;

import br.csi.porkManagerApi.models.Suino.TipoSuino;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "alojamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alojamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    @NotBlank
    @NotNull
    private String nome;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoSuino tipo;
    @Column(name = "capacidade", nullable = false)
    @NotNull
    private Integer capacidade;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusAlojamento status;

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

    @JsonIgnore
    @OneToMany(mappedBy = "alojamento", fetch = FetchType.EAGER)
    private Set<Suino> suinos;
    public int getNumeroSuinosAtual() {
        return this.suinos != null ? this.suinos.size() : 0;
    }

    public enum StatusAlojamento {
        ATIVO,
        INATIVO,
    }
}

