package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Contrat;

@Repository
public interface IContratRepository extends JpaRepository<Contrat, Long> {
}
