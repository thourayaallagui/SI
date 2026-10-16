package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Equipement;

@Repository
public interface IEquipementRepository extends JpaRepository<Equipement, Long> {
}
