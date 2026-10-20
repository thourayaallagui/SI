# Note Technique : API REST, Tests Postman et Documentation Swagger — AutoLoc

**Projet** : AutoLoc API  
**Module** : Atelier 5 — Architecture des Systèmes d'Information (ASI)

---

## Section A. Endpoints de l'API REST

| Contrôleur | Verbe HTTP & Route | Service appelé | Code Succès | Codes d'erreur | Description |
| :--- | :--- | :--- | :---: | :---: | :--- |
| **`ContratController`** | `GET /api/contrats` | `contratService.findAll()` | 200 | — | Liste tous les contrats |
| | `GET /api/contrats/{id}` | `contratService.findById(id)` | 200 | 404 | Consulte un contrat par son ID |
| | `POST /api/contrats` | `contratService.create(contrat)` | 201 | 400 | Crée un contrat (avec ou sans paiements) |
| | `PUT /api/contrats/{id}` | `contratService.update(id, contrat)` | 200 | 400, 404 | Modifie un contrat existant |
| | `DELETE /api/contrats/{id}` | `contratService.deleteById(id)` | 204 | 404 | Supprime un contrat et ses paiements |
| **`PaiementController`** | `GET /api/paiements` | `paiementService.findAll()` | 200 | — | Liste tous les paiements (lecture seule) |
| | `GET /api/paiements/{id}` | `paiementService.findById(id)` | 200 | 404 | Consulte un paiement par son ID |
| | *`POST /api/paiements`* | *(Non exposé)* | — | **405** | Méthode non autorisée (lecture seule) |
| **`AgenceController`** | `GET /api/agences` | `agenceService.findAll()` | 200 | — | Liste toutes les agences |
| | `GET /api/agences/{id}` | `agenceService.findById(id)` | 200 | 404 | Consulte une agence |
| | `POST /api/agences` | `agenceService.create(agence)` | 201 | 400 | Crée une agence |
| | `PUT /api/agences/{id}` | `agenceService.update(id, agence)` | 200 | 400, 404 | Modifie une agence |
| | `DELETE /api/agences/{id}` | `agenceService.deleteById(id)` | 204 | 404 | Supprime une agence |
| **`EmployeController`** | `GET /api/employes` | `employeService.findAll()` | 200 | — | Liste tous les employés |
| | `GET /api/employes/{id}` | `employeService.findById(id)` | 200 | 404 | Consulte un employé |
| | `POST /api/employes` | `employeService.create(employe)` | 201 | 400 | Crée un employé |
| | `PUT /api/employes/{id}` | `employeService.update(id, employe)` | 200 | 400, 404 | Modifie un employé |
| | `DELETE /api/employes/{id}` | `employeService.deleteById(id)` | 204 | 404 | Supprime un employé |
| **`VehiculeController`** | `GET /api/vehicules` | `vehiculeService.findAll()` | 200 | — | Liste tous les véhicules |
| | `GET /api/vehicules/{id}` | `vehiculeService.findById(id)` | 200 | 404 | Consulte un véhicule |
| | `POST /api/vehicules` | `vehiculeService.create(vehicule)` | 201 | 400 | Crée un véhicule |
| | `PUT /api/vehicules/{id}` | `vehiculeService.update(id, vehicule)` | 200 | 400, 404 | Modifie un véhicule |
| | `DELETE /api/vehicules/{id}` | `vehiculeService.deleteById(id)` | 204 | 404 | Supprime un véhicule |
| **`EquipementController`** | `GET /api/equipements` | `equipementService.findAll()` | 200 | — | Liste tous les équipements |
| | `GET /api/equipements/{id}` | `equipementService.findById(id)` | 200 | 404 | Consulte un équipement |
| | `POST /api/equipements` | `equipementService.create(equipement)` | 201 | 400 | Crée un équipement |
| | `PUT /api/equipements/{id}` | `equipementService.update(id, equipement)` | 200 | 400, 404 | Modifie un équipement |
| | `DELETE /api/equipements/{id}` | `equipementService.deleteById(id)` | 204 | 404 | Supprime un équipement |
| **`ClientController`** | `GET /api/clients` | `clientService.findAll()` | 200 | — | Liste tous les clients |
| | `GET /api/clients/{id}` | `clientService.findById(id)` | 200 | 404 | Consulte un client |
| | `POST /api/clients` | `clientService.create(client)` | 201 | 400 | Crée un client |
| | `PUT /api/clients/{id}` | `clientService.update(id, client)` | 200 | 400, 404 | Modifie un client |
| | `DELETE /api/clients/{id}` | `clientService.deleteById(id)` | 204 | 404 | Supprime un client |
| **`ReservationController`**| `GET /api/reservations` | `reservationService.findAll()` | 200 | — | Liste les réservations |
| | `GET /api/reservations/{id}`| `reservationService.findById(id)` | 200 | 404 | Consulte une réservation |
| | `POST /api/reservations` | `reservationService.create(reservation)`| 201 | 400 | Crée une réservation |
| | `PUT /api/reservations/{id}`| `reservationService.update(id, reservation)`| 200 | 400, 404 | Modifie une réservation |
| | `DELETE /api/reservations/{id}`| `reservationService.deleteById(id)` | 204 | 404 | Supprime une réservation |
| **`MaintenanceController`**| `GET /api/maintenances` | `maintenanceService.findAll()` | 200 | — | Liste les maintenances |
| | `GET /api/maintenances/{id}`| `maintenanceService.findById(id)` | 200 | 404 | Consulte une maintenance |
| | `POST /api/maintenances` | `maintenanceService.create(maintenance)`| 201 | 400 | Crée une maintenance |
| | `PUT /api/maintenances/{id}`| `maintenanceService.update(id, maintenance)`| 200 | 400, 404 | Modifie une maintenance |
| | `DELETE /api/maintenances/{id}`| `maintenanceService.deleteById(id)` | 204 | 404 | Supprime une maintenance |

---

## Section B. Annotations Jackson et Sérialisation JSON

| Association | Annotation Retenue | Côté | Justification Métier et Technique |
| :--- | :--- | :--- | :--- |
| **`Contrat` $\leftrightarrow$ `Paiement`** | `@JsonManagedReference("contrat-paiements")` / `@JsonBackReference("contrat-paiements")` | `Contrat.paiements` (liste) / `Paiement.contrat` (parent) | **Composition stricte** : un paiement est créé directement dans le corps JSON du contrat. `@JsonManagedReference` sérialise la liste des paiements lors de la lecture du contrat, tandis que `@JsonBackReference` brise la boucle infinie sans empêcher la liaison bidirectionnelle. |
| **`Agence` $\rightarrow$ `Vehicule` / `Employe`** | `@JsonIgnore` | `Agence.vehicules` et `Agence.employes` | **Agrégation faible** : l'agence ne doit pas embarquer tout le parc automobile ni tous les employés dans sa réponse JSON. |
| **`Client` $\rightarrow$ `Reservation`** | `@JsonIgnore` | `Client.reservations` | Évite le chargement de l'historique complet lors de la lecture d'un client. Ne jamais mettre `@JsonBackReference` sur `Reservation.client` car la propriété serait ignorée lors d'un POST de réservation. |
| **`Vehicule` $\leftrightarrow$ `Equipement`** | `@JsonIgnore` | `Equipement.vehicules` | Brise la récursion de la relation `@ManyToMany`. |
| **`Vehicule` $\rightarrow$ `Maintenance`** | `@JsonIgnore` | `Vehicule.maintenances` | Évite d'alourdir la consultation d'un véhicule. |
| **`Reservation` $\leftrightarrow$ `Contrat`** | `@JsonIgnore` | `Reservation.contrat` | Brise la boucle de la relation `@OneToOne`. |

### Différence fondamentale entre `@JsonBackReference` et `@JsonIgnore`
- **`@JsonBackReference`** : Fait partie d'une paire avec `@JsonManagedReference`. La propriété est omise lors de la sérialisation JSON (pas de boucle), mais Jackson réassocie automatiquement l'instance parente lors de la désérialisation d'un objet imbriqué.
- **`@JsonIgnore`** : Ignore totalement et inconditionnellement le champ, tant en sérialisation (écriture JSON) qu'en désérialisation (lecture JSON). Si un client envoie ce champ dans une requête, il ne sera jamais injecté.

---

## Section C. Complémentarité Postman et Swagger UI

| Outil | Rôle Principal | Exemple Concret dans l'Atelier |
| :--- | :--- | :--- |
| **Postman** | **Tester et automatiser** : Permet de créer des requêtes rejouables, de passer des corps JSON personnalisés, d'enchaîner des scénarios et de valider automatiquement les codes de statut HTTP via des assertions JavaScript (`pm.test()`). | **Scénario Cascade Contrat/Paiement** : Exécution d'un `POST /api/contrats` avec paiements imbriqués, mémorisation de l'ID généré dans `{{contratId}}`, mise à jour via `PUT`, puis vérification du `DELETE` (204) propageant la suppression des paiements (404 sur `GET /api/paiements/{id}`). |
| **Swagger UI** | **Documenter et explorer** : Génère dynamiquement une documentation interactive et synchronisée avec le code source (OpenAPI 3). Permet aux développeurs front-end et partenaires de découvrir le contrat d'interface sans lire le code Java. | Consultation instantanée sur `http://localhost:8080/api/swagger-ui.html` avec les descriptions d'erreurs 400 et 404, et les schémas déduits automatiquement des entités. |

---

## Section D. Analyse de Qualité SonarQube for IDE (SonarLint)

| N° | Règle SonarQube | Problème Détecté | Correction Appliquée |
| :---: | :--- | :--- | :--- |
| **1** | **`java:S6813`** | *"Field dependency injection should not be used"* : L'injection par champ `@Autowired` dans les contrôleurs masque les dépendances et empêche l'instanciation propre hors conteneur. | Utilisation systématique de champs `private final` et de l'annotation Lombok `@RequiredArgsConstructor` sur tous les contrôleurs. |
| **2** | **`java:S1186`** | *"Methods should not be empty"* : Détection de blocs ou méthodes de test sans assertion ou méthodes vides. | Ajout systématique d'assertions dans les tests et suppression de méthodes inutiles. |
| **3** | **Clean Architecture** | *"Controllers should not access repositories directly"* : Couplage direct interdit entre la couche Web et la persistance. | Les 9 contrôleurs dépendent **exclusivement** des interfaces `I...Service`, sans aucune référence vers `JpaRepository`. |

---

## Section E. Réponses aux Questions de Compréhension

### 1. Quel composant reçoit toutes les requêtes, et comment retrouve-t-il la méthode du contrôleur à appeler ?
Toutes les requêtes HTTP entrantes sont interceptées par le contrôleur frontal de Spring MVC : le **`DispatcherServlet`**.  
Pour trouver la méthode à exécuter, il consulte le registre **`HandlerMapping`** (notamment `RequestMappingHandlerMapping`), qui compare la méthode HTTP (GET, POST, etc.) et l'URL de la requête aux annotations `@RequestMapping`, `@GetMapping`, etc. déclarées sur les contrôleurs.

### 2. Pourquoi `ContratController` dépend-il de `IContratService` et non de `ContratRepository` ni de `ContratServiceImpl` ?
- **Non de `ContratRepository`** : Pour respecter la séparation des responsabilités. Le contrôleur gère le protocole HTTP et délègue la logique métier, la validation et les règles de gestion au Service. Il ne doit pas accéder directement aux données brutes.
- **Non de `ContratServiceImpl`** : Pour respecter le principe d'Inversion des Dépendances (DIP des principes SOLID). Dépendre de l'interface `IContratService` garantit un faible couplage et permet de remplacer facilement l'implémentation (par exemple un mock pour tester le contrôleur unitairement).

### 3. Pourquoi un POST renvoie-t-il 201 et un DELETE 204 ? Pourquoi une ressource absente donne-t-elle 404 et non 500 ?
- **POST $\rightarrow$ 201 Created** : Indique que la requête a abouti à la création effective d'une nouvelle ressource sur le serveur (souvent accompagnée de l'identifiant ou de l'URI générée).
- **DELETE $\rightarrow$ 204 No Content** : Indique que l'opération de suppression a été exécutée avec succès et qu'il n'y a plus aucun contenu à renvoyer dans le corps de la réponse.
- **404 Not Found vs 500 Internal Server Error** : Une ressource absente est une condition normale d'utilisation (erreur client liée à un identifiant inexistant). Le code **404** signale que le client a demandé une ressource introuvable. Le code **500** est réservé aux pannes imprévues du serveur (bug non capturé, rupture de base de données, etc.). Renvoyer 500 pour un identifiant inconnu masquerait un comportement attendu en erreur système.

### 4. Pourquoi `PaiementController` n'expose-t-il que des GET ? Quelle réponse reçoit un POST `/api/paiements` ?
- **Pourquoi uniquement des GET** : Un paiement est en composition forte avec son contrat. Il n'a aucun sens fonctionnel sans le contrat auquel il est imputé. Sa création et sa mise à jour se font exclusivement à travers le contrat (`Contrat.paiements` avec `CascadeType.ALL`).
- **Réponse à un POST `/api/paiements`** : Spring MVC renvoie le code HTTP **`405 Method Not Allowed`**, car aucune méthode `@PostMapping` n'est déclarée sur ce chemin.

### 5. Quels risques présente l'exposition directe des entités JPA ? Pourquoi `Contrat.paiements` utilise-t-il `@JsonManagedReference` alors que `Agence.vehicules` utilise `@JsonIgnore` ?
- **Risques de l'exposition directe des entités JPA** :
  1. **Couplage fort** entre le schéma de la base de données et l'API publique (la moindre modification de table casse les clients de l'API).
  2. **Fuite de données sensibles** ou de champs techniques internes (mots de passe, identifiants techniques, drapeaux internes).
  3. **Risque de boucles infinies de sérialisation** et de requêtes $N+1$ non maîtrisées lors du parcours des relations LAZY.
- **Différence d'annotation** :
  - `Contrat.paiements` forme une **composition** : on souhaite explicitement intégrer la liste des paiements dans le JSON d'un contrat lors de sa consultation ou création, d'où l'usage de `@JsonManagedReference` (qui conserve les données tout en coupant la boucle retour).
  - `Agence.vehicules` est une **agrégation faible** : rapatrier tout le catalogue des véhicules à chaque consultation d'une agence serait lourd et inutile pour l'API ; on coupe donc totalement cette sérialisation avec `@JsonIgnore`.
