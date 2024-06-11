package br.csi.porkManagerApi.repositories;

import br.csi.porkManagerApi.models.Saude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaudeRepository extends JpaRepository<Saude, Long> {

    boolean existsBySuinoId(Long suinoId);

}
