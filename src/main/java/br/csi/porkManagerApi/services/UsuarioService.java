package br.csi.porkManagerApi.services;


import br.csi.porkManagerApi.dtos.UsuarioDto;
import br.csi.porkManagerApi.exceptions.InvalidCpfException;
import br.csi.porkManagerApi.exceptions.InvalidEnumException;
import br.csi.porkManagerApi.models.Usuario;
import br.csi.porkManagerApi.repositories.UsuarioRepository;
import br.csi.porkManagerApi.utils.enumUtils.EnumUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public boolean salvarUsuario(UsuarioDto usuarioDto) throws Exception {
        try {
            Optional<Usuario> isUsuario = usuarioRepository.findByCpf(usuarioDto.cpf());

            Usuario.Role role = EnumUtils.stringToEnum(Usuario.Role.class, usuarioDto.role());
            if (role.name().isBlank()) {
                throw new InvalidEnumException("Permissão de usuário Inválida");
            }

            String cpf = usuarioDto.cpf().replaceAll("\\D", "");
            if (cpf.length() != 11) {
                throw new IllegalArgumentException("CPF deve ter 11 números");
            }

            Usuario usuario = new Usuario();
            usuario.setNome(usuarioDto.nome().toLowerCase());
            usuario.setCpf(cpf);
            usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioDto.senha()));
            usuario.setActive(usuarioDto.active());
            usuario.setRole(role);
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public boolean atualizarUsuario(UsuarioDto usuarioDto, Long id) throws Exception {
        try {
            Optional<Usuario> isUsuario = usuarioRepository.findByCpf(usuarioDto.cpf());

            Usuario.Role role = EnumUtils.stringToEnum(Usuario.Role.class, usuarioDto.role());
            if (role.name().isBlank()) {
                throw new InvalidEnumException("Permissão de usuário Inválida");
            }

            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));

            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            if (!bcrypt.matches(usuarioDto.senha(), usuario.getSenha())) {
                usuario.setSenha(bcrypt.encode(usuarioDto.senha()));
            }

            String cpf = usuarioDto.cpf().replaceAll("\\D", "");
            if (cpf.length() != 11) {
                throw new IllegalArgumentException("CPF deve ter 11 números");
            }

            usuario.setNome(usuarioDto.nome().toLowerCase());
            usuario.setCpf(cpf);
            usuario.setActive(usuarioDto.active());
            usuario.setRole(role);
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    @Transactional
    public boolean desativarUsuario(Long id) throws Exception {
        try {
            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));

            usuario.setActive(!usuario.getActive());

            usuarioRepository.save(usuario);
            return usuario.getActive();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional
    public boolean deletarUsuario(Long id) {
        try {
            usuarioRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Usuario getUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

}