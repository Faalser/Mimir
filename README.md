# Mimir

[![Licence : AGPL v3](https://img.shields.io/badge/licence-AGPL--3.0-blue.svg)](LICENSE)
[Java](https://img.shields.io/badge/Java-27-orange.svg)
[JDA](https://img.shields.io/badge/JDA-6.5.0-5865F2.svg)

Mimir est un bot Discord écrit en Java, basé sur [JDA](https://github.com/discord-jda/JDA) et construit avec Maven.

## Fonctionnalités

Mimir fonctionne avec des **commandes slash**. Chaque commande est une classe qui étend une classe abstraite commune; une instance à chaque exécution, et son identifiant, sa data et le ping de la passerelle sont journalisée.

| Commande | Description |
| -------- | ----------- |
| '/ping' | Affiche la latence du bot |
| '/roll' | Lance un dé |
| '/say' | Fait parler le bot |

> La liste des commandes évolue avec le projet.

## Prérequis 

- [JDK 27](https://jdk.java.net/27/)
- [Maven](https://maven.apache.org/download.cgi#preview-4-x-release)
- Une application discord et son token : [Discord Developper Portal](https://discord.com/developers/applications)

## Installation

'''bash
git clone https://forgejo.itwontfix.dev/Faalser/Mimir.git
cd Mimir/Bot
'''

Ou sur GitHub

'''bash
git clone https://github.com/Faalser/Mimit.git
cd Mimir/Bot
'''

Créez un fichier '.env' dans 'Bot/' (il est ignoré par Git) :

'''env
TOKEN_BOT_DISCORD=votre_token_ici
'''

## Compilation et lancement

'''bash
mvn clean package
java -jar target/X.jar
'''

## Structure du projet

'''
Mimir/
|--Bot/                 # Module Maven du bot
|   |--pom.xml
|   |--src/main/java/  # Code source
|--.woodpecker.yml     # Pipeline CI
|--HEADER.txt          # En-tête de license des fichiers source
|--LICENSE             # AGPL-3.0
|--README.md
'''

## Technologies

- [JDA](https://github.com/discord-jda/JDA) 6.5.0 : bibliothèque Discord
- [dotenv-java](https://github.com/cdimascio/dotenv-java) : chargement de la configuration
- [Logback](https://logback.qos.ch/) : journalisation
- [Maven](https://maven.apach.org/) : build et gestion des dépendances
- [Woodpecker CI](https://woodpecker-ci.org) : intégration continue

## Contribuer

Les contributions sont les bienvenues.

1. Forkez le dépôt et créez une branche ('git checkout -b feature/la-fontionnalté')
2. Ajoutez l'en-tête de license (HEADER.txt) en haut de chaque nouveau fichier source ('mvn spotless:apply')
3. Vérifiez que le projet compile ('mvn clean package')
4. Ouvrez un pull request

## License

Ce projet est distribué sous license [GNU AGPL v3](LICENSE).
