# Test Offer - Project

**gras**

*italique*

***les deux***

Le projet test_offer a été conçu dans le cadre de l'évalution de niveau de Mélanie Masson entre décembre 2021 et janvier 2022.


## Introduction

Le projet se compose d'une API Springboot. Elle expose plusieurs services :
• celui qui permet d'enregistrer un utilisateur
• un qui affiche les détails de tous les utilisateurs enregistré
• un qui affiche les détails d'un utilisateur enregistré

Un utilisateur est défini par un nom d'utilisateur, une date de naissance et un pays de résidence qui sont obligatoires ainsi qu'un numéro de téléphone et un genre qui sont facultatifs.
Il y a des contraintes pour les valeurs de ces attributs. En cas de valeur incorrecte, des messages d'erreur et états http appropriés sont renvoyés.

Les utilisateurs sont enregistrés dans une base de données embarquée (h2).
Des tests unitaires et d'intégrations ont été effectués. Une collection Postman est disponible.
Une AOP est utilisée pour enregistrer les entrées et les sorties de chaque appel ainsi que le temps de traitement.


## Avant l'utilisation de l'API Springboot:

### Version Java

La version de Java utilisée dans cette API est la jdk-11.0.2

- Elle est téléchargeable à cette [adresse](https://www.oracle.com/fr/java/technologies/javase/jdk11-archive-downloads.html).

### IDE utilisée

L'IDE utilisée pour cette API est IntelliJ IDEA 2021.3

- Il est téléchargeable à cette [adresse](https://www.jetbrains.com/fr-fr/idea/download/).


### Téléchargement et ouverture de l'API Springboot:

Il est possible de télécharger directement l'ensemble du projet à partir de GitHub. Il faut ensuite le décompresser et l'ouvrir.

On peut également cloner le projet grace à l'outil Git. Pour cela on se place dans le dossier de son choix, on ouvre une console GitBash et on entre la commande `git clone https://github.com/MelanieMasson/test_offer`.

Le projet peut ensuite être ouvert avec l'IDE de votre choix (IntelliJ, Eclipse, VSCode ...).


## L'API

### SpringBoot

La version de SpringBoot utilisée pour cette API est la 2.5.6.

### Base de données

L'API utilise une base de données embarquée H2 nommée test_atos. L'execution du fichier data.sql permet sa mise à jour et son remplissage.


## Utilisation de l'API

### Démarrage de la base de donnée embarquée

On commence par executer le fichier `data.sql` situé à la base du répertoire `test_offer/src/main/ressources/`. Après l'avoir ouvert on sélectionne l'ensemble du fichier, on fait un clic droit et on sélectionne 'Executer'.
La base de données sera réinitialisée et contiendra alors 3 utilisateurs de base.


### Démarrage du projet

On va ensuite executer le fichier `TestOfferApplication` situé à la base du répertoire `test_offer/src/main/java/com/atos/test_offer/`.

### Services disponibles

L'API implémente trois services :
- Le premier affiche les détails de tous les utilisateurs enregistrés.
Pour cela il faudra entrer l'URL `http://localhost:8080/api/user` dans une page web ou sur Postman. La requête renvoit toutes les informations des utilisateurs enregistrés dans la base de données.

- Le second affiche les détails d'un utilisateur spécifique.
Pour cela on entre l'URL `http://localhost:8080/api/user/{id}` où `{id}` est l'identifiant de l'utilisateur dont on veut récupérer les informations. Si aucun utilisateur ne porte cet identifiant, une erreur 404 est envoyée.

- Le dernier permet d'enregistrer un utilisateur.
On utilise à nouveau l'URL `http://localhost:8080/api/user`. La requête Post doit être écrite en format JSON.

Un utilisateur est défini par un nom d'utilisateur, une date de naissance et un pays de résidence qui sont obligatoires ainsi qu'un numéro de téléphone et un genre qui sont facultatifs.
- La taille du nom d'utilisateur doit être comprise entre 2 et 30 caractères. Tous les types de caractères sont acceptés.
- Le pays entré doit avoir une longueur minimale de 2 caractères. Si le pays entré n'est pas la France, un message d'erreur est renvoyé. Les valeurs `France`, `france` ou `FRANCE` sont acceptées sans distinction.
- La date de naissance doit être dans le format `yyyy-mm-dd`. Si la date est postérieure à la date du jour ou dans un format incorrect, une erreur est renvoyée. De plus, l'utilisateur doit avoir 18 ans ou plus pour être enregistré.
- Le genre n'est pas obligatoire mais s'il est entré les possibiltés `Male`, `Female` et `Other`. Si une valeur est entrée mais est invalide un message d'erreur est renvoyé.
- Le numéro de téléphone n'est pas obligatoire non plus. Comme l'utilisateur est un résident français, on considère que seuls les formats de numéro de téléphone français sont acceptés (exemple : 0123456789, 01.23.45.67.89, +33-1.23.45.67.89, +33(0) 123 456 789 ... ). Si une valeur est entrée mais est invalide un message d'erreur est renvoyé.


## Test

Les tests unitaires et tests d'intégration sont dans le répertoire `src/test/java/com/atos/test_offer/`.

### Tests d'intégration

Les tests d'intégration sont divisés en trois.
- Tests avec l'API et Postman
L'API SpringBoot doit être executée. On envoie les requêtes Get et Post via Postman selon les informations données précedemment.
Une collection de requêtes Postman est disponible dans le répertoire `src/test/java/com/atos/test_offer/` sous le nom de Postman_collection.
La collection commence par les requêtes Get puis les requêtes Post.
