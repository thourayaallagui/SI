# Note Technique : Stratégie de Chargement (Fetch) et de Propagation (Cascade) — AutoLoc

## 1. Principes Généraux Appliqués

Dans le cadre du projet **AutoLoc**, les choix d'associations JPA répondent aux bonnes pratiques d'architecture logicielle et de performance de la persistance relationnelle :

1. **Stratégie de Fetch (Chargement)** :
   - Par défaut, JPA configure `@ManyToOne` et `@OneToOne` en `EAGER`. Ce comportement par défaut a été **systématiquement forcé à `LAZY`** afin d'éviter le problème de requêtage en cascade (problème $N+1$) et le chargement inutile de graphes d'objets entiers lors d'une simple requête.
   - Les collections `@OneToMany` et `@ManyToMany` sont conservées en `LAZY` (défaut JPA), garantissant qu'une collection n'est chargée que lorsqu'elle est expressément sollicitée dans une transaction.

2. **Stratégie de Cascade et cycle de vie** :
   - La cascade n'est appliquée que lorsqu'il existe une relation de composition forte ou de dépendance de cycle de vie (ex. un paiement n'a pas de sens sans son contrat).
   - Pour les agrégations faibles (ex. une agence et ses véhicules/employés), **aucune cascade** n'est définie : la suppression d'une agence ne doit en aucun cas entraîner la destruction physique des véhicules ni le licenciement des employés.
   - `orphanRemoval = true` est réservé aux collections composées dont les éléments retirés ne peuvent subsister comme orphelins en base de données.

---

## 2. Tableau Récapitulatif des Associations

| Association | Type JPA | Côté Propriétaire (Clé Étrangère / Table) | Fetch | Cascade / orphanRemoval | Justification Métier & Technique |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Contrat $\leftrightarrow$ Paiement** | `@OneToMany` / `@ManyToOne` (bidirectionnelle) | `Paiement` (`contrat_id_contrat`) | `LAZY` | `CascadeType.ALL` + `orphanRemoval = true` | Relation de composition stricte. Un paiement n'existe que rattaché à son contrat. Supprimer un contrat supprime ses paiements (`REMOVE`). Retirer un paiement de la liste le supprime de la base (`orphanRemoval`). `LAZY` évite d'alourdir la consultation simple d'un contrat. |
| **Agence $\rightarrow$ Vehicule** | `@OneToMany` / `@ManyToOne` (bidirectionnelle) | `Vehicule` (`id_agence`) | `LAZY` | **Aucune** | Agrégation faible. Un véhicule survit à la fermeture ou suppression de son agence de rattachement (il peut être réaffecté à une autre agence). `LAZY` évite de charger l'ensemble du parc automobile lors de la lecture d'une agence. |
| **Agence $\rightarrow$ Employe** | `@OneToMany` / `@ManyToOne` (bidirectionnelle) | `Employe` (`id_agence`) | `LAZY` | **Aucune** | L'employé est une entité autonome qui survit à l'agence (mutation, réaffectation). `LAZY` évite de rapatrier la liste complète des salariés lors de l'accès à une agence. |
| **Vehicule $\leftrightarrow$ Equipement** | `@ManyToMany` (bidirectionnelle) | `Vehicule` (table de jointure `vehicule_equipement`) | `LAZY` | **Aucune** | Les équipements (GPS, siège bébé, etc.) sont partagés et réutilisables par plusieurs véhicules. La suppression d'un véhicule ne doit jamais supprimer l'équipement du catalogue. Utilisation d'un `Set` pour garantir l'unicité et optimiser les opérations de collection. |
| **Client $\rightarrow$ Reservation** | `@OneToMany` / `@ManyToOne` (bidirectionnelle) | `Reservation` (`id_client`) | `LAZY` | `CascadeType.PERSIST` | Permet d'enregistrer automatiquement de nouvelles réservations créées directement via l'objet client. Pas de cascade `REMOVE` pour conserver l'historique comptable et d'activité même en cas de modification de profil client. |
| **Reservation $\rightarrow$ Vehicule** | `@ManyToOne` (unidirectionnelle) | `Reservation` (`id_vehicule`) | `LAZY` | **Aucune** | La réservation fait référence à un véhicule loué. Le véhicule est indépendant et ne doit en aucun cas être impacté par les opérations du cycle de vie de la réservation. |
| **Reservation $\leftrightarrow$ Contrat** | `@OneToOne` (bidirectionnelle) | `Contrat` (`id_reservation`) | `LAZY` | `CascadeType.ALL` (côté `Reservation`) | Un contrat formalise juridiquement une réservation unique. La réservation est le point d'entrée métier : la création ou annulation/suppression de la réservation propage ses opérations au contrat associé. `LAZY` évite une jointure systématique. |
| **Vehicule $\rightarrow$ Maintenance** | `@OneToMany` / `@ManyToOne` (bidirectionnelle) | `Maintenance` (`id_vehicule`) | `LAZY` | `CascadeType.PERSIST` | Facilite la planification de nouvelles interventions de maintenance directement depuis l'entité véhicule. Aucune cascade de suppression pour préserver l'historique technique du carnet d'entretien du véhicule. |

---

## 3. Bonnes Pratiques Clean Code Adoptées

- **Lombok ciblé** : Utilisation exclusive de `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor` au niveau des entités. Bannissement délibéré de `@Data` pour écarter tout risque de récursion infinie (`StackOverflowError`) sur les méthodes `toString()`, `equals()` et `hashCode()` traversant les relations bidirectionnelles.
- **Initialisation des collections** : Toutes les collections (`List`, `Set`) sont initialisées à vide (`new ArrayList<>()`, `new HashSet<>()`) afin d'éviter les `NullPointerException` lors des manipulations en mémoire.
- **Clarté du schéma relationnel** : Respect des conventions de nommage des clés étrangères (`id_agence`, `id_client`, `id_vehicule`, `id_reservation`) et de la table de jointure explicite `vehicule_equipement`.
