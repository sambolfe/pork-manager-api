package br.csi.porkManagerApi.repositories;

import br.csi.porkManagerApi.dtos.SuinoResponseDto;
import br.csi.porkManagerApi.models.Suino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuinoRepository extends JpaRepository<Suino, Long> {
    Optional<Suino> findByIdentificacaoOrelha(String identificacaoOrelha);

    @Query("SELECT DISTINCT s.identificacaoOrelha FROM Suino s")
    List<String> findAllIdentificadoresOrelha();

}