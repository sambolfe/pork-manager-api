package br.csi.porkManagerApi.controllers;


import br.csi.porkManagerApi.dtos.AlojamentoDto;
import br.csi.porkManagerApi.dtos.AlojamentoWithNumeroSuinosDto;
import br.csi.porkManagerApi.models.Alojamento;
import br.csi.porkManagerApi.services.AlojamentoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/alojamento")
public class AlojamentoController {

    private final AlojamentoService alojamentoService;

    public AlojamentoController(AlojamentoService alojamentoService) {
        this.alojamentoService = alojamentoService;
    }

    @PostMapping("/saveAlojamento")
    public ResponseEntity<Alojamento> salvarAlojamento(@Valid @RequestBody AlojamentoDto alojamentoDto) throws Exception {
        Alojamento savedAlojamento = alojamentoService.salvarAlojamento(alojamentoDto);
        return new ResponseEntity<>(savedAlojamento, HttpStatus.OK);
    }

    @PutMapping("/updateAlojamento/{id}")
    public ResponseEntity<Alojamento> atualizarAlojamento(
            @Valid @RequestBody AlojamentoDto alojamentoDto,
            @Valid @PathVariable Long id
    ) throws Exception {
        Alojamento updatedAlojamento = alojamentoService.atualizarAlojamento(id, alojamentoDto);
        return new ResponseEntity<>(updatedAlojamento, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAlojamento/{id}")
    public ResponseEntity<?> deletarAlojamento(@Valid @PathVariable Long id) throws Exception {
        try {
            boolean deleted = alojamentoService.deletarAlojamento(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Não foi possível excluir o alojamento. Verifique se o ID está correto.", HttpStatus.BAD_REQUEST);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Não é possível excluir o alojamento, pois está sendo referenciado por outras entidades.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAlojamento/{id}")
    public ResponseEntity<Alojamento> buscarAlojamento(@Valid @PathVariable Long id) {
        try {
            Alojamento alojamento = alojamentoService.buscarAlojamento(id);
            return new ResponseEntity<>(alojamento, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllAlojamentos")
    public List<AlojamentoWithNumeroSuinosDto> getAllAlojamentos() {
        List<Alojamento> alojamentos = alojamentoService.listarAlojamentos();
        List<AlojamentoWithNumeroSuinosDto> dtos = new ArrayList<>();

        for (Alojamento alojamento : alojamentos) {
            AlojamentoWithNumeroSuinosDto dto = new AlojamentoWithNumeroSuinosDto();
            dto.setId(alojamento.getId());
            dto.setNome(alojamento.getNome());
            dto.setTipo(alojamento.getTipo());
            dto.setCapacidade(alojamento.getCapacidade());
            dto.setStatus(alojamento.getStatus());
            dto.setNumeroSuinosAtual(alojamento.getNumeroSuinosAtual());

            dtos.add(dto);
        }

        return dtos;
    }

}

