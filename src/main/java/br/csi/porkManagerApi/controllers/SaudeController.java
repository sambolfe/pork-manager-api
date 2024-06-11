package br.csi.porkManagerApi.controllers;

import br.csi.porkManagerApi.dtos.SaudeDto;
import br.csi.porkManagerApi.dtos.SaudeResponseDto;
import br.csi.porkManagerApi.exceptions.InvalidRequestDataException;
import br.csi.porkManagerApi.models.Saude;
import br.csi.porkManagerApi.models.Suino;
import br.csi.porkManagerApi.services.SaudeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saude")
public class SaudeController {
    private final SaudeService saudeService;
    public SaudeController(SaudeService saudeService) {
        this.saudeService = saudeService;
    }

    @PostMapping("/saveSaude")
    public ResponseEntity<Saude> salvarSaude(@Valid @RequestBody SaudeDto saudeDto) throws Exception {
        if(isValidDto(saudeDto)) {
            Saude savedSaude = saudeService.salvarSaude(saudeDto);
            if(savedSaude != null) {
                return new ResponseEntity<>(savedSaude, HttpStatus.OK);
            }
        }
        throw new InvalidRequestDataException("Os dados enviados são inválidos");
    }

    @PutMapping("/updateSaude/{id}")
    public ResponseEntity<Saude> salvarSaude(@Valid @RequestBody SaudeDto saudeDto, @Valid @PathVariable Long id) throws Exception {
        if(isValidDto(saudeDto) && id != null) {
            Saude updatedSaude = saudeService.atualizarSaude(saudeDto, id);
            if(updatedSaude != null) {
                return new ResponseEntity<>(updatedSaude, HttpStatus.OK);
            }
        }
        throw new InvalidRequestDataException("Os dados enviados são inválidos");
    }

    @GetMapping("/getSaude/{id}")
    public ResponseEntity<Saude> getSaude(@Valid @PathVariable Long id)  {
        if (id != null) {
            Saude res = saudeService.getSaude(id);
            if (res != null) {
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            throw new EntityNotFoundException("Registro de Saude não encontrado!");
        }
        throw new InvalidRequestDataException("Identificador de saude não encontrado!");
    }
    @GetMapping("/getAllSaudes")
    public ResponseEntity<List<SaudeResponseDto>> getAllSaudes() {
        List<SaudeResponseDto> saudeResponseDtos = saudeService.getAllSaudes();
        return new ResponseEntity<>(saudeResponseDtos, HttpStatus.OK);
    }

    @DeleteMapping("/deleteSaude/{id}")
    public ResponseEntity<?> deletarSaude(@Valid @PathVariable Long id) throws Exception {
        try {
            ResponseEntity<?> response = saudeService.deletarSaude(id);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok().build(); // Retorna 200 OK se a exclusão for bem-sucedida
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o registro não for encontrado
            } else {
                return ResponseEntity.badRequest().body(response.getBody()); // Retorna 400 Bad Request se ocorrer outro erro
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Não é possível excluir o alojamento, pois está sendo referenciado por outras entidades.");
        }
    }

    private boolean isValidDto(SaudeDto saudeDto) {
        return !saudeDto.observacoes().isBlank() &&
                !saudeDto.tipoTratamento().isBlank() &&
                !saudeDto.dataInicioTratamento().isBlank() &&
                saudeDto.peso() != null &&
                (saudeDto.dataEntradaCio() == null || !saudeDto.dataEntradaCio().isBlank()) &&
                (saudeDto.idSuino() == null || saudeDto.idSuino() > 0);
    }
}
