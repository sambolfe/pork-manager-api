package br.csi.porkManagerApi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    @NotBlank
    @NotNull
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    @NotBlank
    @NotNull
    @Size(max = 11)
    private String cpf;

    @Column(name = "senha", nullable = false)
    @NotBlank
    @NotNull
    private String senha;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "roles", nullable = false)
    @NotNull
    private Role role;

    @Column(name = "active", nullable = false)
    @NotNull
    private Boolean active;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Suino> suinos;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }
    @Override
    @JsonIgnore
    public String getPassword() {
        return this.senha;
    }
    @Override
    @JsonIgnore
    public String getUsername() {
        return this.cpf;
    }
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.active;
    }

    public enum Role {
        ADMIN,
        CRIADOR
    }


}
