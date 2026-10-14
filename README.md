# Mimir

[![Licence : AGPL v3](https://img.shields.io/github/license/Faalser/Mimir?style=for-the-badge&color=blue)](LICENSE)
[![Java](https://img.shields.io/badge/Java-27-orange?style=for-the-badge)](https://jdk.java.net/27/)
[![JDA](https://img.shields.io/github/v/release/discord-jda/JDA?include_prereleases&sort=date&display_name=tag&style=for-the-badge)](https://github.com/discord-jda/JDA)
[![Maven](https://img.shields.io/badge/Maven-3.9+-red?style=for-the-badge)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-336791?style=for-the-badge)](https://www.postgresql.org/)

Mimir est un bot Discord écrit en Java, basé sur [JDA](https://github.com/discord-jda/JDA) et construit avec Maven. Il intègre un système de niveaux où l'activité des membres (messages et temps en vocal) est convertie en XP.

## Fonctionnalités

Mimir fonctionne avec des **commandes slash**. Chaque commande est une classe qui étend une classe abstraite commune ; une instance est créée à chaque exécution, et son identifiant, sa data et le ping de la passerelle sont journalisés.

| Commande | Description |
| -------- | ----------- |
| `/clear` | Efface les messages |
| `/help` | Affiche l'aide |
| `/ping` | Affiche la latence du bot |
| `/roll` | Lance un dé |
| `/say` | Fait parler le bot |
| `/stat` | Affiche les statistiques de l'utilisateur |

### Système de niveaux

Chaque membre gagne de l'expérience (XP) selon son activité :

| Activité | XP gagnée |
| -------- | --------- |
| Envoyer un message | 10 XP |
| 1 seconde en vocal | 5 XP |

Le niveau est calculé selon la formule : `niveau = √(XP / 1000)`

Les statistiques (niveau, XP, messages envoyés, temps en vocal) sont consultables avec `/stat` et persistées dans une base PostgreSQL.

> La liste des commandes évolue avec le projet.

## Prérequis

- [JDK 27](https://jdk.java.net/27/)
- [Maven 3.9+](https://maven.apache.org/download.cgi)
- [PostgreSQL 16+](https://www.postgresql.org/download/)
- Une application Discord et son token : [Discord Developer Portal](https://discord.com/developers/applications)

## Installation

Via Forgejo :
```bash
git clone https://forgejo.itwontfix.dev/Faalser/Mimir.git
cd Mimir/Bot
```

Via GitHub :
```bash
git clone https://github.com/Faalser/Mimir.git
cd Mimir/Bot
```

## Configuration

Créez un fichier `.env` dans `Bot/` (ignoré par Git) pour le token Discord :

```env
TOKEN_BOT_DISCORD=votre_token_ici
```

Créez un fichier `.properties` dans `Bot/` (également ignoré par Git) pour la connexion PostgreSQL :

```properties
DRIVER=org.postgresql.Driver
URL=jdbc:postgresql://localhost:5432/mimir
LOGIN=votre_user
PASSWORD=votre_mot_de_passe
```

## Compilation et lancement

```bash
mvn clean package
java -jar target/Mimir.jar
```

## Structure du projet

```
Mimir/
├── Bot/                        # Module Maven du bot
│   ├── src/main/
│   │   ├── java/
│   │   │   ├── mimir/          # Point d'entrée (Mimir.java)
│   │   │   ├── commands/       # Commandes slash (Clear, Help, Ping, Roll, Say, Stat...)
│   │   │   ├── listeners/      # Écouteurs d'événements (messages, vocal)
│   │   │   ├── registry/       # Registre des commandes
│   │   │   └── database/       # Couche d'accès PostgreSQL
│   │   └── resources/
│   │       └── logback.xml     # Configuration des logs
│   └── pom.xml                 # Configuration Maven
├── .woodpecker.yml             # Pipeline CI
├── HEADER.txt                  # En-tête de licence des fichiers source
├── LICENSE                     # AGPL-3.0
└── README.md
```

## Technologies

| Technologie | Rôle |
| ----------- | ---- |
| [JDA](https://github.com/discord-jda/JDA) 6.5.0 | Bibliothèque Discord |
| [PostgreSQL](https://www.postgresql.org/) | Base de données |
| [dotenv-java](https://github.com/cdimascio/dotenv-java) | Chargement de la configuration |
| [Logback](https://logback.qos.ch/) | Journalisation |
| [Maven](https://maven.apache.org/) | Build et gestion des dépendances |
| [Maven Shade](https://maven.apache.org/plugins/maven-shade-plugin/) | Création du JAR exécutable |
| [Woodpecker CI](https://woodpecker-ci.org) | Intégration continue |

## Contribuer

Les contributions sont les bienvenues.

1. Forkez le dépôt
2. Créez une branche pour votre fonctionnalité (`git checkout -b feature/la-fonctionnalite`)
3. Ajoutez l'en-tête de licence (HEADER.txt) en haut de chaque nouveau fichier source (`mvn spotless:apply`)
4. Vérifiez que le projet compile (`mvn clean package`)
5. Ouvrez un pull request

## Soutenir le projet

Rejoignez le serveur Discord communautaire de Mimir :

[![Discord](https://img.shields.io/discord/1420467694507135106?style=for-the-badge&logo=discord&label=Discord&color=5865F2)](https://discord.gg/STHZ7McGZh)

## Contact

- Email : contact@itwontfix.dev
- Code source : [Forgejo](https://forgejo.itwontfix.dev/Faalser/Mimir)

## Licence

Ce projet est distribué sous licence [GNU AGPL v3](LICENSE).

## Auteur

- [@Faalser](https://forgejo.itwontfix.dev/Faalser)
