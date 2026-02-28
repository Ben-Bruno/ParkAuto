# ParkAuto
Projet POO de gestion d'un parc auto-mobile
Description

Application desktop développée en Java avec JavaFX permettant la gestion d’un parc automobile.

Le système permet d’administrer un ensemble de véhicules, de gérer leur disponibilité, leur affectation et leur suivi opérationnel via une interface graphique moderne.

L’objectif du projet est de démontrer la maîtrise :

du développement d’interfaces graphiques en JavaFX

de l’architecture MVC

de la programmation orientée objet en Java

de la gestion de données via JDBC

de la structuration propre d’un projet logiciel desktop

Fonctionnalités principales

Ajout, modification et suppression de véhicules (CRUD)

Gestion des catégories de véhicules (utilitaire, tourisme, professionnel, etc.)

Suivi de disponibilité des véhicules

Affectation à un utilisateur ou service

Consultation détaillée des informations

Recherche dynamique par immatriculation ou type

Validation des données saisies

Gestion des erreurs utilisateur

Architecture du projet

Le projet est structuré selon une architecture MVC (Model-View-Controller).

Model

Contient les entités métier :

Vehicule

Utilisateur

Affectation

Responsabilités :

Encapsulation des données

Logique métier

Validation interne

View

Interface graphique développée avec JavaFX :

FXML

Scene Builder

Composants JavaFX (TableView, TextField, Button, ComboBox)

Gestion des événements utilisateur

Responsabilités :

Affichage des données

Interaction utilisateur

Mise à jour dynamique de l’interface

Controller

Couche intermédiaire :

Gestion des événements (Event Handling)

Communication entre la vue et le modèle

Appels aux services métier

Mise à jour des composants UI

Technologies utilisées

Java 17

JavaFX

JDBC

SQL

PostgreSQL / MySQL

Maven

Architecture MVC

POO (Encapsulation, Héritage, Polymorphisme)

Gestion des exceptions

Collections Java (ArrayList, ObservableList)

Gestion des données

Deux modes possibles :

Stockage en mémoire (ObservableList)

Persistance via base de données relationnelle avec JDBC

Fonctionnalités base de données :

Connexion sécurisée

Requêtes SQL

PreparedStatement

Gestion des transactions

Concepts Java mis en œuvre

Programmation Orientée Objet

Architecture MVC

Encapsulation des entités

Gestion des événements JavaFX

Liaison de propriétés (Property Binding)

Gestion des exceptions personnalisées

Séparation des responsabilités

Structuration en packages

Structure du projet
src/
 ├── model/
 │     ├── Vehicule.java
 │     ├── Utilisateur.java
 │     └── Affectation.java
 │
 ├── controller/
 │     └── VehiculeController.java
 │
 ├── view/
 │     ├── main.fxml
 │     └── styles.css
 │
 ├── service/
 │     └── VehiculeService.java
 │
 └── Main.java
Sécurité et validation

Vérification des champs obligatoires

Validation format immatriculation

Contrôle cohérence disponibilité

Gestion exceptions métier

Protection contre saisies invalides

Objectifs pédagogiques

Concevoir une application desktop professionnelle

Maîtriser JavaFX et la gestion d’interface graphique

Implémenter une architecture MVC propre

Connecter une application Java à une base SQL

Structurer un projet orienté objet maintenable

Améliorations possibles

Système d’authentification utilisateur

Gestion des rôles (admin, gestionnaire)

Tableau de bord statistique

Export CSV ou PDF

Tests unitaires JUnit

Migration vers architecture client-serveur

Exposition API REST Spring Boot

Auteur

Bruno Bengono
