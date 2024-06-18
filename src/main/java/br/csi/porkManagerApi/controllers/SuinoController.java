package br.csi.porkManagerApi.controllers;


import br.csi.porkManagerApi.dtos.SuinoDto;
import br.csi.porkManagerApi.dtos.SuinoIdentificadorDto;
import br.csi.porkManagerApi.dtos.SuinoResponseDto;
import br.csi.porkManagerApi.dtos.SuinoUpdateDto;
import br.csi.porkManagerApi.exceptions.InvalidRequestDataException;
import br.csi.porkManagerApi.models.Suino;
import br.csi.porkManagerApi.services.SuinoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suino")
public class SuinoController {
    private final SuinoService suinoService;

    public SuinoController(SuinoService suinoService) {
        this.suinoService = suinoService;
    }

    @PostMapping("/saveSuino")
    public ResponseEntity<String> salvarSuino(@Valid @RequestBody SuinoDto suinoDto) throws Exception {
        if (isValidDto(suinoDto)) {
            Suino suino = suinoService.salvarSuino(suinoDto);
            if (suino != null) {
                return new ResponseEntity<>("Suino salvo com sucesso!", HttpStatus.OK);
            }
            return new ResponseEntity<>("Erro ao salvar dados do suino!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        throw new InvalidRequestDataException("Os dados enviados para salvar o suino são inválidos!");
    }

    @PutMapping("/updateSuino/{id}")
    public ResponseEntity<String> atualizarSuino(
            @Valid @RequestBody SuinoUpdateDto suinoDto,
            @Valid @PathVariable Long id
    ) throws Exception {
        if (isValidUpdateDto(suinoDto)) {
            Suino suino = suinoService.atualizarSuino(id, suinoDto);
            if (suino != null) {
                return new ResponseEntity<>("Suino atualizado com sucesso!", HttpStatus.OK);
            }
            return new ResponseEntity<>("Erro ao atualizar dados do suino!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        throw new InvalidRequestDataException("Os dados enviados para atualizar o suino são inválidos!");
    }

    @DeleteMapping("/deleteSuino/{id}")
    public ResponseEntity<Boolean> deletarSuino(@Valid @PathVariable Long id) throws Exception {
        if (id != null) {
            boolean res = suinoService.deletarSuino(id);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new InvalidRequestDataException("Identificador do suino não encontrado!");
    }

    @GetMapping("/getSuinoByOrelha/{identificacaoOrelha}")
    public ResponseEntity<Suino> getSuinoByOrelha(@Valid @PathVariable String identificacaoOrelha)  {
        if (identificacaoOrelha != null && !identificacaoOrelha.isEmpty()) {
            Suino res = suinoService.getSuinoByIdentificacaoOrelha(identificacaoOrelha);
            if (res != null) {
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            throw new EntityNotFoundException("Suíno não encontrado com o número de identificação de orelha: " + identificacaoOrelha);
        }
        throw new InvalidRequestDataException("Identificação da orelha do suíno não encontrada!");
    }

    @GetMapping("/getAllSuinos")
    public ResponseEntity<List<SuinoResponseDto>> getAllSuinos() {
        List<SuinoResponseDto> suinos = suinoService.getAllSuinos();
        return new ResponseEntity<>(suinos, HttpStatus.OK);
    }

    @GetMapping("/getSuino/{id}")
    public ResponseEntity<SuinoResponseDto> getSuino(@PathVariable Long id) {
        SuinoResponseDto suino = suinoService.getSuino(id);
        return ResponseEntity.ok().body(suino);
    }

    @GetMapping("/getAllIdentificadoresOrelha")
    public ResponseEntity<List<SuinoIdentificadorDto>> getAllIdentificadoresOrelha() {
        List<SuinoIdentificadorDto> identificadoresOrelha = suinoService.getAllIdentificadoresOrelhaComIdSuino();
        return ResponseEntity.ok(identificadoresOrelha);
    }
    private boolean isValidUpdateDto(SuinoUpdateDto suinoDto) {
        return suinoDto.identificacaoOrelha() != null
                && !suinoDto.identificacaoOrelha().isBlank()
                && !suinoDto.observacoes().isBlank()
                && suinoDto.observacoes() != null
                && suinoDto.tipoSuino() != null
                && suinoDto.sexo() != null
                && suinoDto.dataNasc() != null
                && suinoDto.idRaca() != null
                && suinoDto.idUsuario() != null;
    }


    private boolean isValidDto(SuinoDto suinoDto) {
        return suinoDto.idRaca() != null &&
                suinoDto.identificacaoOrelha() != null && !suinoDto.identificacaoOrelha().isBlank() &&
                suinoDto.dataNasc() != null &&
                suinoDto.sexo() != null &&
                suinoDto.tipoSuino() != null &&
                suinoDto.idUsuario() != null;
    }
}
