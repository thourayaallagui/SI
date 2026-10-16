package tn.esprit.autoloc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class VehiculeDataInitializer implements CommandLineRunner {

    private final VehiculeRepository vehiculeRepository;

    @Override
    public void run(String... args) {
        if (vehiculeRepository.count() == 0) {
            log.info("Insertion des vehicules de demonstration au demarrage...");
            Vehicule v1 = new Vehicule(null, "200-TN-1234", "Renault", "Clio", CategorieVehicule.CITADINE, new BigDecimal("80.00"), StatutVehicule.DISPONIBLE);
            Vehicule v2 = new Vehicule(null, "201-TN-5678", "Peugeot", "3008", CategorieVehicule.SUV, new BigDecimal("150.00"), StatutVehicule.DISPONIBLE);
            Vehicule v3 = new Vehicule(null, "202-TN-9012", "Mercedes", "Classe C", CategorieVehicule.BERLINE, new BigDecimal("250.00"), StatutVehicule.LOUE);

            vehiculeRepository.saveAll(List.of(v1, v2, v3));
            log.info("3 vehicules inseres avec succes dans la base de donnees !");
        }
    }
}
