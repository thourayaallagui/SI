package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Reservation;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}
