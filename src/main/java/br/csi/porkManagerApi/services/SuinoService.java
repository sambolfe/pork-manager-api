package br.csi.porkManagerApi.services;

import br.csi.porkManagerApi.dtos.SuinoDto;
import br.csi.porkManagerApi.dtos.SuinoIdentificadorDto;
import br.csi.porkManagerApi.dtos.SuinoResponseDto;
import br.csi.porkManagerApi.dtos.SuinoUpdateDto;
import br.csi.porkManagerApi.exceptions.InvalidEnumException;
import br.csi.porkManagerApi.models.Suino;
import br.csi.porkManagerApi.models.Raca;
import br.csi.porkManagerApi.models.Usuario;
import br.csi.porkManagerApi.models.Alojamento;  // Importando o modelo de Alojamento
import br.csi.porkManagerApi.repositories.SuinoRepository;
import br.csi.porkManagerApi.repositories.RacaRepository;
import br.csi.porkManagerApi.repositories.UsuarioRepository;
import br.csi.porkManagerApi.repositories.AlojamentoRepository;  // Importando o repositório de Alojamento
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuinoService {
    private final SuinoRepository suinoRepository;
    private final RacaRepository racaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlojamentoRepository alojamentoRepository;

    public SuinoService(SuinoRepository suinoRepository, RacaRepository racaRepository,
                        UsuarioRepository usuarioRepository, AlojamentoRepository alojamentoRepository) {
        this.suinoRepository = suinoRepository;
        this.racaRepository = racaRepository;
        this.usuarioRepository = usuarioRepository;
        this.alojamentoRepository = alojamentoRepository;
    }

    public Suino getSuino(Long id) {
        return suinoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Suino não encontrado com o ID: " + id));
    }

    @Transactional
    public Suino salvarSuino(SuinoDto suinoDto) throws Exception {
        try {
            Raca raca = racaRepository.findById(suinoDto.idRaca())
                    .orElseThrow(() -> new EntityNotFoundException("Raça não encontrada com o ID: " + suinoDto.idRaca()));

            Usuario usuario = usuarioRepository.findById(suinoDto.idUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + suinoDto.idUsuario()));

            Alojamento alojamento = alojamentoRepository.findById(suinoDto.alojamentoId())  // Buscando o Alojamento pelo ID
                    .orElseThrow(() -> new EntityNotFoundException("Alojamento não encontrado com o ID: " + suinoDto.alojamentoId()));

            Suino.SexoSuino sexo = Suino.SexoSuino.valueOf(suinoDto.sexo());
            Suino.TipoSuino tipoSuino = Suino.TipoSuino.valueOf(suinoDto.tipoSuino());

            if (sexo.name().isBlank()) {
                throw new InvalidEnumException("Sexo inválido!");
            }

            if (tipoSuino.name().isBlank()) {
                throw new InvalidEnumException("Tipo suíno inválido!");
            }

            Date currentDate = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(suinoDto.dataNasc());

            Suino suino = new Suino();
            suino.setRaca(raca);
            suino.setIdentificacaoOrelha(suinoDto.identificacaoOrelha());
            suino.setDataNasc(date);
            suino.setSexo(sexo);
            suino.setObservacoes(suinoDto.observacoes());
            suino.setTipoSuino(tipoSuino);
            suino.setAlojamento(alojamento);
            suino.setCriadoEm(currentDate);
            suino.setAtualizadoEm(currentDate);
            suino.setUsuario(usuario);

            return suinoRepository.save(suino);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }



    @Transactional
    public Suino atualizarSuino(Long id, SuinoUpdateDto suinoDto) throws Exception {
        try {
            Suino suino = suinoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Suino não encontrado com o ID: " + id));

            Date currentDate = new Date(System.currentTimeMillis());

            Raca raca = racaRepository.findById(suinoDto.idRaca())
                    .orElseThrow(() -> new EntityNotFoundException("Raça não encontrada com o ID: " + suinoDto.idRaca()));

            Usuario usuario = usuarioRepository.findById(suinoDto.idUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + suinoDto.idUsuario()));

            Alojamento alojamento = alojamentoRepository.findById(suinoDto.alojamentoId())  // Buscando o Alojamento pelo ID
                    .orElseThrow(() -> new EntityNotFoundException("Alojamento não encontrado com o ID: " + suinoDto.alojamentoId()));

            Suino.SexoSuino sexo = Suino.SexoSuino.valueOf(suinoDto.sexo());
            Suino.TipoSuino tipoSuino = Suino.TipoSuino.valueOf(suinoDto.tipoSuino());

            if (sexo.name().isBlank()) {
                throw new InvalidEnumException("Sexo inválido!");
            }

            if (tipoSuino.name().isBlank()) {
                throw new InvalidEnumException("Tipo suíno inválido!");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(suinoDto.dataNasc());

            suino.setRaca(raca);
            suino.setIdentificacaoOrelha(suinoDto.identificacaoOrelha());
            suino.setDataNasc(date);
            suino.setSexo(sexo);
            suino.setObservacoes(suinoDto.observacoes());
            suino.setTipoSuino(tipoSuino);
            suino.setAlojamento(alojamento);
            suino.setAtualizadoEm(currentDate);
            suino.setUsuario(usuario);

            return suinoRepository.save(suino);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    @Transactional
    public boolean deletarSuino(Long id) {
        try {
            suinoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Suino getSuinoByIdentificacaoOrelha(String identificacaoOrelha) {
        return suinoRepository.findByIdentificacaoOrelha(identificacaoOrelha)
                .orElseThrow(() -> new EntityNotFoundException("Suino não encontrado com a identificação da orelha: " + identificacaoOrelha));
    }

    public List<SuinoIdentificadorDto> getAllIdentificadoresOrelhaComIdSuino() {
        List<Suino> suinos = suinoRepository.findAll();
        return suinos.stream()
                .map(suino -> new SuinoIdentificadorDto(suino.getId(), suino.getIdentificacaoOrelha()))
                .collect(Collectors.toList());
    }

    public List<SuinoResponseDto> getAllSuinos() {
        List<Suino> suinos = suinoRepository.findAll();
        return suinos.stream()
                .map(this::mapSuinoToResponseDto)
                .collect(Collectors.toList());
    }

    private SuinoResponseDto mapSuinoToResponseDto(Suino suino) {
        SuinoResponseDto dto = new SuinoResponseDto();
        dto.setId(suino.getId());
        dto.setIdRaca(suino.getRaca().getId());
        dto.setNomeRaca(suino.getRaca().getNome());
        dto.setIdentificacaoOrelha(suino.getIdentificacaoOrelha());
        dto.setDataNasc(suino.getDataNasc().toString());
        dto.setSexo(suino.getSexo().toString());
        dto.setObservacoes(suino.getObservacoes());
        dto.setTipoSuino(suino.getTipoSuino().toString());
        dto.setIdUsuario(suino.getUsuario().getId());
        dto.setNomeUsuario(suino.getUsuario().getNome());
        dto.setAlojamentoId(suino.getAlojamento().getId());
        dto.setNomeAlojamento(suino.getAlojamento().getNome());
        return dto;
    }
}
